package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import services._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class SprintIssueController[F[_]: Async](
  sprintIssueService: SprintIssueService[F]
) extends BaseController
    with SprintIssueEndpoints {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(put, post, list, delete)

}
