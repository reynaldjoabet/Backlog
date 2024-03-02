package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
class SprintController() extends BaseController {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil
}
