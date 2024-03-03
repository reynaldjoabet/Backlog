package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import sttp.tapir.json.circe._
import http.requests._
trait EpicEndpoints extends BaseEndpoints {
  private val base = secureBaseEndpoints
    .in("api" / "v1")
    .tag("Epic")

  val post = base.post
    .in("epic")
    .in(jsonBody[CreateEpicRequest])
    .out(jsonBody[Epic]) // Epic

  val list = base.get
    .in("epic")
    .out(jsonBody[List[Epic]])
  val get = base.get
    .in("epic")
    .in(path[Long]("id"))
    .out(jsonBody[Epic])

  val delete = base.delete
    .in("epic")
    .in(path[Long]("id"))

}
