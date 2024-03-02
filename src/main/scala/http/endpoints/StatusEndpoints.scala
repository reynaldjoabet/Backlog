package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait StatusEndpoints extends BaseEndpoints {

  val base = secureBaseEndpoints
    .in("api" / "v8")
    .tag("Status")

  val get = base.in("states") // .in Status

  val list = base.get.in("states")
}
