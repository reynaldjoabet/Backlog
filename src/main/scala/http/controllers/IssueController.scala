package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import services._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class IssueController[F[_]: Async](issueService: IssueService[F])
    extends BaseController
    with IssueEndpoints {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(get, put, putSprint, delete, post)

}
