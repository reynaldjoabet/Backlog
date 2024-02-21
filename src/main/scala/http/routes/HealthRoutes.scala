package http.routes

import cats.Monad
import org.http4s._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import org.http4s.server.AuthMiddleware
import domain.User
import services.CatsRedisServiceLive._
import services.CatsRedisServiceLive
import cats.effect._
import middleware.CookieAuthenticationMiddleware
final case class HealthRoutes(
) extends Http4sDsl[IO] {

  private[routes] val prefixPath = "/healthcheck"

  private val httpRoutes: AuthedRoutes[User, IO] = AuthedRoutes.of[User, IO] {
    case GET -> Root as user =>
      Ok()
  }

  def routes(authMiddleware: AuthMiddleware[IO, User]): HttpRoutes[IO] = Router(
    "path" -> authMiddleware(httpRoutes)
  )

  val redis = CatsRedisServiceLive(CatsRedisServiceLive.resource)

  val userAuthMiddleware: AuthMiddleware[IO, User] =
    CookieAuthenticationMiddleware[IO, User](redis)

  val route: HttpRoutes[IO] = routes(userAuthMiddleware)

}
