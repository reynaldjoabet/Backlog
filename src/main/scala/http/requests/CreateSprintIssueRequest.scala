package http
package requests
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema
final case class CreateSprintIssueRequest(
    sprintIssueId: Long,
    projectName: String,
    issueType: String,
    summary: String,
    description: String,
    assignee: String,
    sprintName: String,
    epicName: String,
    reqOfTesting: Boolean,
    spDeveloping: Int,
    spTesting: Int,
    totalSP: Int,
    priority: String,
    reporter: String
)

object CreateSprintIssueRequest {
  implicit val codec: Codec.AsObject[CreateSprintIssueRequest] =
    deriveCodec[CreateSprintIssueRequest]
  implicit val schema: sttp.tapir.Schema[CreateSprintIssueRequest] =
    Schema.derived[CreateSprintIssueRequest]
}
