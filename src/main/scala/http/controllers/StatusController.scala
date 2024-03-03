package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.kernel.Async
import http.endpoints._

class StatusController[F[_]: Async]()
    extends BaseController
    with StatusEndpoints {
  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(post, list)
}
