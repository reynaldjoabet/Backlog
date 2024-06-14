package http.endpoints

import java.nio.charset.StandardCharsets

import domain._
import http.requests._
import sttp.capabilities.fs2.Fs2Streams
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe._

trait SprintIssueEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints.in("api" / "v3").tag("SprintIssues")

  val post = base
    .post
    .in("sprintissues")
    .in(jsonBody[CreateSprintIssueRequest])
    .out(jsonBody[SprintIssue])
  // .in//SprintIssue

  val put = base
    .put
    .in("sprintissues")
    .in(path[Long]("SprintIssueId"))
    .in(jsonBody[CreateSprintIssueRequest])
    .out(jsonBody[SprintIssue])

  val list = base.get.out(jsonBody[List[SprintIssue]])

  def list1[F[_]] = base
    .get
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )

  val delete = base.delete.in("sprintissues").in(path[Long]("SprintIssueId"))

}
