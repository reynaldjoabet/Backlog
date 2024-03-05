package http.routes
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.server.Router

final case class SprintRoutes[F[_]: Async](sprintService: SprintService[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v3/sprints"

  val routes = AuthedRoutes.of[User, F] {
    case GET -> Root / LongVar(sprintId) as user => ???
    case GET -> Root as user                     => ??? // list
    case req @ POST -> Root as user =>
      req.req.as[CreateIssueRequest]
      ???
    case req @ PUT -> Root / LongVar(sprintId) as user =>
      req.req.as[CreateIssueRequest]
      ???
    case DELETE -> Root / LongVar(sprintId) as user => ???

  }
}
