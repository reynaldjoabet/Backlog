package http.routes.secured
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.server._

final case class StatusRoutes[F[_]: Async](statusService: StatusService[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v8/states"

  private val httpRoutes = AuthedRoutes.of[User, F] {
    case GET -> Root as user => ???

    case req @ POST -> Root as user =>
      req.req.as[CreateStatusRequest]
      ???
  }

  def routes(authMiddleware: AuthMiddleware[F, User]): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
