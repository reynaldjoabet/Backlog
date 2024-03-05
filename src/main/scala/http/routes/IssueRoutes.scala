package http.routes
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._

import http.requests._
import domain._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.server.Router
import org.http4s.AuthedRoutes

final case class IssueRoutes[F[_]: Async](issueService: IssueService[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v1/issues"

  val routes = AuthedRoutes.of[User, F] {
    case GET -> Root / LongVar(issueId) as user    => ???
    case DELETE -> Root / LongVar(issueId) as user => ???
    case GET -> Root as user                       => ???
    case req @ PUT -> Root / "sprint" as user =>
      req.req.as[CreateIssueRequest]
      ???

    case req @ POST -> Root as user =>
      req.req.as[CreateIssueRequest]
      ???
    case req @ PUT -> Root as user =>
      req.req.as[CreateIssueRequest]
      ???
  }
}
