package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import services._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class EmailController[F[_]: Async](emailService: EmailService[F])
    extends BaseController
    with EmailEndpoints {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil
}
