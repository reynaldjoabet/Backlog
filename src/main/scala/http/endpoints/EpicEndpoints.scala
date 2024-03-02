package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait EpicEndpoints extends BaseEndpoints {
  val base = secureBaseEndpoints
    .in("api" / "v1")
    .tag("epic")
  val post = base.post.in("epic") // Epic

  val list = base.get.in("epic")
  val get = base.get.in("epic").in(path[Long]("id"))
}
