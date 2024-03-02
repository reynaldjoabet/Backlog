package http.routes
import cats.Monad
import org.http4s.dsl.Http4sDsl
import services._
final case class PriorityRoutes[F[_]: Monad](
    priorityService: PriorityService[F]
) extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v5"
}
