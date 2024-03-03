package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import sttp.tapir.json.circe._
import domain._
import http.requests._
trait DurationEndpoints extends BaseEndpoints {
  private val base = secureBaseEndpoints
    .in("api" / "v6")
    .tag("Durations")

  val post = base.post
    .in("durations")
    .in(jsonBody[CreateDurationRequest])
    .out(jsonBody[Duration])

  val list = base.get
    .in("durations")
    .out(jsonBody[List[Duration]])
}
