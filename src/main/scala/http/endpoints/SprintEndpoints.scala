package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait SprintEndpoints extends BaseEndpoints {

  val base = secureBaseEndpoints
    .in("api" / "v2")
    .tag("Sprints")

  val post = base.post.in("sprints")
  // inSprint

  val list = base.get.in("sprints")

  val get = base.get.in("sprints").in(path[Long]("SprintId"))

  val delete =
    base.delete.in("sprints").in(path[Long]("SprintId"))
  val put =
    base.put.in("sprints").in(path[Long]("SprintId")) // inSprint

}
