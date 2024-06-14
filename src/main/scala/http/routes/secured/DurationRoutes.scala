package http.routes.secured

import cats.effect.Async

import domain._
import http.requests._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import org.http4s.server._
import services._

final case class DurationRoutes[F[_]: Async](
  durationService: DurationService[F]
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/api/v6"

  private val httpRoutes = AuthedRoutes.of[User, F] {
    case GET -> Root / "durations" as user => ???
    case req @ POST -> Root / "durations" as user =>
      req.req.as[CreateDurationRequest]
      ???
  }

  def routes(authMiddleware: AuthMiddleware[F, User]): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )

}
