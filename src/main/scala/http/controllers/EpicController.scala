package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
class EpicController() extends BaseController {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil
}
