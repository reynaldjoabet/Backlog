package http.controllers

import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir._
import sttp.model.StatusCode
import sttp.tapir.server.http4s.Http4sServerInterpreter
import http.endpoints._

class HealthController() extends BaseController with HealthEndpoints {
  val health = healthEndpoint
    .serverLogicSuccess(_ => IO("All good"))

  val errorRoute = errorEndpoint
    .serverLogicError(_ => IO(StatusCode.InternalServerError))
  override val routes: List[ServerEndpoint[Any, IO]] = List(health, errorRoute)

  val li = List(healthEndpoint, errorEndpoint)

}
