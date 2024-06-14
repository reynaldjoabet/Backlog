package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import services._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class StatusController[F[_]: Async](statusService: StatusService[F])
    extends BaseController
    with StatusEndpoints {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(post, list)

}
