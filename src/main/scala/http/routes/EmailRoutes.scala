package http.routes
import cats.Monad
import org.http4s.dsl.Http4sDsl
import services._

final case class EmailRoutes[F[_]: Monad](emailService: EmailService[F])
    extends Http4sDsl[F] {

  private[routes] val prefixPath = ""
}
