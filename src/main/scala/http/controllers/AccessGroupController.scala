package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class AccessGroupController[F[_]: Async]() extends BaseController with AccessGroupEndpoints {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil
}
