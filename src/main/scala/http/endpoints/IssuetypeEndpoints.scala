package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import sttp.tapir.json.circe._
import http.requests._
import sttp.capabilities.fs2.Fs2Streams
import java.nio.charset.StandardCharsets
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

  def list1[F[_]] = base.get
    .in("issuetypes")
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )

  val get = base.get
    .in("issuetypes")
    .out(jsonBody[IssueType])
    .in(path[Long]("IssuetypeId"))
}
