package http.routes.secured
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.server._

final case class ProjectRoutes[F[_]: Async](projectService: ProjectService[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "api/v7/projects"

  private val httpRoutes = AuthedRoutes.of[User, F] {
    case GET -> Root as user => ???

    case req @ PUT -> Root / LongVar(id) as user =>
      req.req.as[CreateProjectRequest]
      ???

    case req @ POST -> Root as user =>
      req.req.as[CreateProjectRequest]
      ???

    case DELETE -> Root / LongVar(id) as user => ???
  }

  def routes(authMiddleware: AuthMiddleware[F, User]): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
