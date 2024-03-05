package http.routes
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.server.Router

final case class ProjectRoutes[F[_]: Async](projectService: ProjectService[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "api/v7/projects"

  val routes = AuthedRoutes.of[User, F] {
    case GET -> Root as user => ???

    case req @ PUT -> Root / LongVar(id) as user =>
      req.req.as[CreateProjectRequest]
      ???

    case req @ POST -> Root as user =>
      req.req.as[CreateProjectRequest]
      ???

    case DELETE -> Root / LongVar(id) as user => ???
  }
}
