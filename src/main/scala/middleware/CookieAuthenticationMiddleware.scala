package middleware
import org.http4s.server.AuthMiddleware
import org.http4s.headers.Authorization
import org.http4s._
import cats.Monad
import cats.data._
import cats.implicits._
import cats.effect.syntax.all._
import domain._
import cats.MonadError
import cats.MonadThrow
import org.http4s.dsl.Http4sDsl
import org.http4s.headers._
import services._
import services.CatsRedisServiceLive._
import io.circe.Decoder
import cats.effect.IO

object CookieAuthenticationMiddleware {

  //({ type Y[X] = OptionT[F, X] })#Y: This is a type lambda that defines a new type Y in terms of OptionT[F, X]`
  private def authenticateUser[F[_]: Monad, T: Decoder](
      redisService: RedisService[F]
  ): Kleisli[({ type Y[X] = OptionT[F, X] })#Y, Request[F], T] = Kleisli {
    req: Request[F] =>
      req.cookies
        .find(_.name == "cookiename")
        .map(_.content) match {
        case Some(cookie) =>
          OptionT.liftF(redisService.get[T](cookie)).flatMap {
            case Some(value) =>
              OptionT.pure[F](value)
            case None => OptionT.none[F, T]
          }
        case None => OptionT.none[F, T]
      }
  }
  private def authenticateUser1[F[_]: Monad, T: Decoder](
      redisService: RedisService[F]
  ): Kleisli[({ type Y[X] = OptionT[F, X] })#Y, Request[F], T] = Kleisli {
    req: Request[F] =>
      req.cookies
        .find(_.name == "cookiename")
        .map(_.content) match {
        case Some(cookie) =>
          OptionT[F, T](redisService.get[T](cookie)).flatMap(OptionT.pure[F](_))
        case None => OptionT.none[F, T]
      }
  }

  private def authenticateUser0[F[_]: Monad](
      jwtService: JWTService[F]
  ): Kleisli[({ type Y[X] = OptionT[F, X] })#Y, Request[F], UserID] = Kleisli {
    req: Request[F] =>
      req.cookies
        .find(_.name == "cookiename")
        .map(_.content) match {
        case Some(cookie) =>
          OptionT.liftF(jwtService.verifyToken1(cookie)).flatMap {
            case Some(userId) =>
              OptionT.pure[F](userId)
            case _ => OptionT.none[F, UserID]
          }
        case None => OptionT.none[F, UserID]
      }
  }

sealed abstract class AuthenticationError

case object UnauthorizedResponse extends AuthenticationError

case object ForbiddenResponse extends AuthenticationError

def onFailure[F[_]: Monad]: AuthedRoutes[AuthenticationError, F] =
  Kleisli { request =>
    val dsl = Http4sDsl[F]
    import dsl._
    request.context match {
      case UnauthorizedResponse =>
        OptionT.liftF(
          Unauthorized.apply(
            `WWW-Authenticate`(Challenge("Bearer", "issuer.toString")),
            request.context.toString()
          )
        )
      case ForbiddenResponse    =>
        OptionT.liftF(Forbidden.apply(""))
    }

  }
  private def authenticateUser6[F[_]: MonadThrow](
      redisService: RedisService[F]
  )(implicit f: Decoder[User]): Kleisli[F, Request[F], Either[AuthenticationError, User]] = Kleisli {
    req: Request[F] =>
      req.cookies
        .filter(_.name == "XSESSION")
        .headOption
        .map(_.content)
        .fold(
          (UnauthorizedResponse: AuthenticationError).asLeft[User].pure[F]
        ) { case sessionId /* cookie */ =>
          redisService.get(sessionId)
            .map {
              case Some(user)  =>
                Either.right[AuthenticationError, User](user)
              case None                                             =>
                Either.left[AuthenticationError, User](UnauthorizedResponse)
            }
            .recover { case _ =>
              Either.left[AuthenticationError, User](UnauthorizedResponse)
            }
        }

  }

  def apply[F[_]: Monad](
      redisService: RedisService[F]
  ): AuthMiddleware[F, User] =
    AuthMiddleware(authenticateUser[F, User](redisService))

  def apply[F[_]: Monad, T: Decoder](
      redisService: RedisService[F]
  ): AuthMiddleware[F, T] =
    AuthMiddleware(authenticateUser1[F, T](redisService))
  def apply1[F[_]: Monad, T: Decoder](
      redisService: RedisService[F]
  ): AuthMiddleware[F, T] =
    AuthMiddleware(authenticateUser[F, T](redisService))

    def apply3[F[_]: MonadThrow](
        redisService: RedisService[F]
     ): AuthMiddleware[F, User] =
       AuthMiddleware(
         authenticateUser6[F](redisService),
         onFailure[F]
       )

}
