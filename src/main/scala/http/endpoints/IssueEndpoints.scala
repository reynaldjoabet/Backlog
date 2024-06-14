package http.endpoints

import domain._
import http.requests._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe._

trait IssueEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints.in("api" / "v1" / "issues").tag("Issues")

  val post      = base.post.in(jsonBody[CreateIssueRequest]).out(jsonBody[Issue])
  val put       = base.put.in(jsonBody[CreateIssueRequest]).out(jsonBody[Issue])
  val putSprint = base.put.in(jsonBody[CreateIssueRequest]).in("sprint").out(jsonBody[Issue])
  val get       = base.get.in(path[Long]("IssueId")).out(jsonBody[Issue])

  val delete = base.delete.in(path[Long]("IssueId"))

}
