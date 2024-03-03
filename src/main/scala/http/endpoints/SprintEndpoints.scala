package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import sttp.tapir.json.circe._
import http.requests._
trait SprintEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints
    .in("api" / "v2")
    .tag("Sprints")

  val post = base.post
    .in("sprints")
    .in(jsonBody[CreateSprintRequest])
    .out(jsonBody[Sprint])
  // inSprint

  val list = base.get
    .in("sprints")
    .out(jsonBody[List[Sprint]])

  val get = base.get
    .in("sprints")
    .in(path[Long]("SprintId"))
    .out(jsonBody[Sprint])

  val delete =
    base.delete
      .in("sprints")
      .in(path[Long]("SprintId"))

  val put =
    base.put
      .in("sprints")
      .in(path[Long]("SprintId"))
      .in(jsonBody[CreateSprintRequest])
      .out(jsonBody[Sprint]) // inSprint

}
