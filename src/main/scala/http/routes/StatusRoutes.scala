package http.routes
import cats.Monad
import org.http4s.dsl.Http4sDsl
import services._
final case class StatusRoutes[F[_]: Monad](statusRoutes: StatusRoutes[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v8"
}
