package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import sttp.tapir.json.circe._
import http.requests._
trait PriorityEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints
    .in("api" / "v5")
    .tag("Priority")

  val post = base.post
    .in("priorities")
    .in(jsonBody[CreatePriorityRequest])
    .out(jsonBody[Priority])

  val list = base.get
    .in("priorities")
    .out(jsonBody[List[Priority]])
}
