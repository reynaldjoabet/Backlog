package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import services._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class DurationController[F[_]: Async](durationService: DurationService[F])
    extends BaseController
    with DurationEndpoints {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(list, post)

}
