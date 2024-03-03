package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import http.endpoints._
import cats.effect.kernel.Async
class AccessGroupController[F[_]: Async]()
    extends BaseController
    with AccessGroupEndpoints {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil
}
