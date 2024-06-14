package http.routes.secured

import cats.effect.Async

import domain._
import http.requests._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import org.http4s.server._
import org.http4s.AuthedRoutes
import org.http4s.HttpRoutes
import services._

final case class IssueRoutes[F[_]: Async](issueService: IssueService[F]) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/api/v1/issues"

  private val httpRoutes = AuthedRoutes.of[User, F] {
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

  def routes(authMiddleware: AuthMiddleware[F, User]): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )

}
