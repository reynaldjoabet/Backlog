package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.kernel.Async
import http.endpoints._
import services._

class SprintController[F[_]: Async](sprintService: SprintService[F])
    extends BaseController
    with SprintEndpoints {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(get, put, post, delete, list)
}
