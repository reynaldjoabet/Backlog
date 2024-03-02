package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.kernel.Async
class DurationController[F[_]: Async]() extends BaseController {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil
}
