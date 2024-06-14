package http.controllers

import cats.effect.IO

import sttp.tapir.server.ServerEndpoint

trait BaseController {

  val routes: List[ServerEndpoint[Any, IO]]

}
