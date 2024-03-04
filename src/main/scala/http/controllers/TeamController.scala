package http.controllers

import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import http.endpoints.TeamEndpoints
import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.syntax.all._
import cats.effect.kernel.Async
import cats.syntax.all._
import http.endpoints._

import services._
class TeamController[F[_]: Async](teamService: TeamService[F])
    extends BaseController
    with TeamEndpoints {

  // val getRoute=get.serverLogic(_ =>Async[F].delay(()).attempt.map(_.))

  override val routes: List[ServerEndpoint[Any, IO]] = List()

  val li = List(put, delete, post, list, delete)

}
