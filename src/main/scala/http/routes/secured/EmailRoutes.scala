package http.routes.secured

import cats.effect.Async

import domain._
import http.requests._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import services._

final case class EmailRoutes[F[_]: Async](emailService: EmailService[F]) extends Http4sDsl[F] {

  private[routes] val prefixPath = ""
}
