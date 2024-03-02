package http.controllers

import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import http.endpoints.HealthEndpoints
import sttp.tapir._
import sttp.model.StatusCode

class HealthController() extends BaseController with HealthEndpoints {
  val health = healthEndpoint
    .serverLogicSuccess(_ => IO("All good"))

  val errorRoute = errorEndpoint
    .serverLogicError(_ => IO(StatusCode.InternalServerError))
  override val routes: List[ServerEndpoint[Any, IO]] = List(health, errorRoute)

}
