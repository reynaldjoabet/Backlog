package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait PriorityEndpoints extends BaseEndpoints {

  val base = secureBaseEndpoints
    .in("api" / "v5")
    .tag("Priority")

  val post = base.post.in("priorities")

  val list = base.get.in("priorities")
}
