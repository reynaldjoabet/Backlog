package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.kernel.Async
import http.endpoints._
import services._

class SprintIssueController[F[_]: Async](
    sprintIssueService: SprintIssueService[F]
) extends BaseController
    with SprintIssueEndpoints {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(put, post, list, delete)
}
