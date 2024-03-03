package domain
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema
final case class SprintIssue(
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

object SprintIssue {
  implicit val codec: Codec.AsObject[SprintIssue] = deriveCodec[SprintIssue]
  implicit val schema: sttp.tapir.Schema[domain.SprintIssue] =
    Schema.derived[SprintIssue]
}
