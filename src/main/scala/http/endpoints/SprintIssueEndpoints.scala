package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import sttp.tapir.json.circe._
import http.requests._
trait SprintIssueEndpoints extends BaseEndpoints {
  private val base = secureBaseEndpoints
    .in("api" / "v3")
    .tag("SprintIssues")

  val post = base.post
    .in("sprintissues")
    .in(jsonBody[CreateSprintIssueRequest])
    .out(jsonBody[SprintIssue])
  // .in//SprintIssue

  val put = base.put
    .in("sprintissues")
    .in(path[Long]("SprintIssueId"))
    .in(jsonBody[CreateSprintIssueRequest])
    .out(jsonBody[SprintIssue])
  val list = base.get
    .out(jsonBody[List[SprintIssue]])
  val delete = base.delete
    .in("sprintissues")
    .in(path[Long]("SprintIssueId"))

}
