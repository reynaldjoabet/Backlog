package http.controllers
import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import cats.effect.kernel.Async
import http.endpoints._
class SystemUserController[F[_]: Async]()
    extends BaseController
    with SystemUserEndpoints {

  override val routes: List[ServerEndpoint[Any, IO]] = Nil

  val li = List(sendEmail, put, get, delete, list, post)
}
