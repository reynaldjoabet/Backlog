package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait DurationEndpoints extends BaseEndpoints {
  val base = secureBaseEndpoints
    .in("api" / "v6")
    .tag("durations")

  val post = base.post.in("durations")

  val list = base.get.in("durations")
}
