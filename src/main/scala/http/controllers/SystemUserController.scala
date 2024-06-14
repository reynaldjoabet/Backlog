package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import services._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class SystemUserController[F[_]: Async](systemUserService: SystemUserService[F])
    extends BaseController
    with SystemUserEndpoints {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(sendEmail, put, get, delete, list, post)

}
