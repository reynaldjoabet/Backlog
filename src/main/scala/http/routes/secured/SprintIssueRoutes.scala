package http.routes.secured
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.server._

final case class SprintIssueRoutes[F[_]: Async](
    sprintIssueService: SprintIssueService[F]
) extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v2/sprintissues"

  private val httpRoutes = AuthedRoutes.of[User, F] {
    case GET -> Root as user                          => ???
    case GET -> Root / LongVar(sprintIssueId) as user => ???
    case req @ POST -> Root as user =>
      req.req.as[CreateSprintIssueRequest]
      ???

    case req @ PUT -> Root / LongVar(sprintIssueId) as user =>
      req.req.as[CreateSprintIssueRequest]
      ???

    case DELETE -> Root / LongVar(sprintIssueId) as user => ???

  }

  def routes(authMiddleware: AuthMiddleware[F, User]): HttpRoutes[F] = Router(
      prefixPath -> authMiddleware(httpRoutes)
    )
}
