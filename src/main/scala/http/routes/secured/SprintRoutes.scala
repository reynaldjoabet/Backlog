package http.routes.secured

import cats.effect.Async

import domain._
import http.requests._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import org.http4s.server._
import services._

final case class SprintRoutes[F[_]: Async](sprintService: SprintService[F]) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/api/v3/sprints"

  private val httpRoutes = AuthedRoutes.of[User, F] {
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

  def routes(authMiddleware: AuthMiddleware[F, User]): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )

}
