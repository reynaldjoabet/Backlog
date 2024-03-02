package http.controllers

import sttp.tapir.server.ServerEndpoint
import cats.effect.IO

trait BaseController {

  val routes: List[ServerEndpoint[Any, IO]]

}
