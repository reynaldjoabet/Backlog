package http.routes.secured
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s.server.Router
final case class EmailRoutes[F[_]: Async](emailService: EmailService[F])
    extends Http4sDsl[F] {

  private[routes] val prefixPath = ""
}
