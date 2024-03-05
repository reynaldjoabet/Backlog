package http.routes.secured
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.server._

final case class TeamRoutes[F[_]: Async](teamService: TeamService[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v9/teams"

  private val httpRoutes = AuthedRoutes.of[User, F] {
    case GET -> Root / "existing-teams" as user => ??? // list

    case req @ POST -> Root as user =>
      req.req.as[CreateTeamRequest]
      ???
    case req @ PUT -> Root / LongVar(id) as user =>
      req.req.as[CreateTeamRequest]
      ???
    case DELETE -> Root / LongVar(id) as user =>
      ???

    case GET -> Root / LongVar(id) as user =>
      ???
  }

  def routes(authMiddleware: AuthMiddleware[F, User]): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
