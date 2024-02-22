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

  val redis = CatsRedisServiceLive(CatsRedisServiceLive.resource)

  val userAuthMiddleware: AuthMiddleware[IO, User] = apply[IO, User](redis)

  val adminAuthMiddleware: AuthMiddleware[IO, User] = apply[IO, User](redis)

}
