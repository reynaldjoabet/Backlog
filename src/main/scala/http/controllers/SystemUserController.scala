package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
class SystemUserController() extends BaseController {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil
}
