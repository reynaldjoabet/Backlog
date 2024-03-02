package http.controllers

import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import http.endpoints.TeamEndpoints
import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.syntax.all._
import cats.effect.kernel.Async
import cats.syntax.all._
class TeamController[F[_]: Async]() extends BaseController with TeamEndpoints {

  // val getRoute=get.serverLogic(_ =>Async[F].delay(()).attempt.map(_.))

  override val routes: List[ServerEndpoint[Any, IO]] = List()

}
