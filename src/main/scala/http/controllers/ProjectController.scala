package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.kernel.Async
import http.endpoints._
import services._

class ProjectController[F[_]: Async](projectService: ProjectService[F])
    extends BaseController
    with ProjectEndpoints {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(delete, post, put, list)
}
