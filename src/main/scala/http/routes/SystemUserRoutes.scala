package http.routes
import cats.Monad
import org.http4s.dsl.Http4sDsl
import services._
final case class SystemUserRoutes[F[_]: Monad](
    systemUserService: SystemUserService[F],
    emailService: EmailService[F]
) extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v1"
}
