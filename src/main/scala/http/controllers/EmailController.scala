package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
class EmailController() extends BaseController {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil
}
