package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait IssueEndpoints extends BaseEndpoints {
  val base = secureBaseEndpoints
    .in("api" / "v1" / "issues")
    .tag("Issues")

  val post = base.post
  val put = base.put
  val putSprint = base.put.in("sprint")
  val get = base.get.in(path[Long]("IssueId"))

  val delete = base.delete.in(path[Long]("IssueId"))
}
