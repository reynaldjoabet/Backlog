package http.controllers

import cats.effect.IO

import http.endpoints._
import services._
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

class HealthController() extends BaseController with HealthEndpoints {

  val health = healthEndpoint.serverLogicSuccess(_ => IO("All good"))

  val errorRoute                                     = errorEndpoint.serverLogicError(_ => IO(StatusCode.InternalServerError))
  override val routes: List[ServerEndpoint[Any, IO]] = List(health, errorRoute)

  val li = List(healthEndpoint, errorEndpoint)

}
