package http.routes
import cats.Monad
import org.http4s.dsl.Http4sDsl
import services._
final case class TeamRoutes[F[_]: Monad](teamService: TeamService[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v9/teams"
}
