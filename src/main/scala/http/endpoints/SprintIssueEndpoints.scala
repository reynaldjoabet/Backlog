package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait SprintIssueEndpoints extends BaseEndpoints {
  val base = secureBaseEndpoints
    .in("api" / "v3")
    .tag("SprintIssues")

  val post = base.post.in("sprintissues")
  // .in//SprintIssue

  val put = base.put
    .in("sprintissues")
    .in(path[Long]("SprintIssueId"))

  val list = base.get
  val delete = base.delete
    .in("sprintissues")
    .in(path[Long]("SprintIssueId"))

}
