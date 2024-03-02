package http.routes
import cats.Monad
import org.http4s.dsl.Http4sDsl
import services._
final case class SprintRoutes[F[_]: Monad](sprintService: SprintService[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v3"
}
