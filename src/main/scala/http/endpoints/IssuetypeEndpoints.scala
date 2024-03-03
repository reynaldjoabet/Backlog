package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import sttp.tapir.json.circe._
import http.requests._
trait IssuetypeEndpoints extends BaseEndpoints {
  private val base = secureBaseEndpoints
    .in("api" / "v4")
    .tag("IssueType") // Issuetype

  val post = base.post
    .in("issuetypes")
    .in(jsonBody[CreateIssueTypeRequest])
    .out(jsonBody[IssueType])

  val list = base.get
    .in("issuetypes")
    .out(jsonBody[List[IssueType]])

  val get = base.get
    .in("issuetypes")
    .out(jsonBody[IssueType])
    .in(path[Long]("IssuetypeId"))
}
