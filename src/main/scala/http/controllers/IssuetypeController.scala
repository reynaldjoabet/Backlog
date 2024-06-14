package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import services._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class IssuetypeController[F[_]: Async](issueTypeService: IssueTypeService[F])
    extends BaseController
    with IssuetypeEndpoints {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(get, post, list)

}
