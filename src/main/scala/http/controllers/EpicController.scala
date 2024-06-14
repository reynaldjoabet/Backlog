package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import services._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class EpicController[F[_]: Async](epicService: EpicService[F])
    extends BaseController
    with EpicEndpoints {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(list, post, get)

}
