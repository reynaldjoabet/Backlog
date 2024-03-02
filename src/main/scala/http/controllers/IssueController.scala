package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
class IssueController() extends BaseController {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil
}
