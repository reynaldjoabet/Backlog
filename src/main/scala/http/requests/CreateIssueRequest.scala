package http
package requests
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema
final case class CreateIssueRequest(
    issueId: Long,
    projectName: String,
    issueTypeName: String,
    summary: String,
    description: String,
    assignee: String,
    epicName: String,
    reqOfTesting: Boolean,
    spDeveloping: Int,
    spTesting: Int,
    totalSP: Int,
    priority: String,
    reporter: String,
    status: String,
    sprintName: String,
    sprintId: Long
)

object CreateIssueRequest {
  implicit val codec: Codec.AsObject[CreateIssueRequest] =
    deriveCodec[CreateIssueRequest]
  implicit val schema: sttp.tapir.Schema[CreateIssueRequest] =
    Schema.derived[CreateIssueRequest]
}
