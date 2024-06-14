package http.controllers

import cats.effect.kernel.Async
import cats.effect.IO

import http.endpoints._
import services._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class ProjectController[F[_]: Async](projectService: ProjectService[F])
    extends BaseController
    with ProjectEndpoints {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(delete, post, put, list)

}
