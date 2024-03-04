package domain
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema
final case class Issue(
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

object Issue {
  implicit val codec: Codec.AsObject[domain.Issue] = deriveCodec[Issue]
  implicit val schema: Schema[domain.Issue] = Schema.derived[Issue]
}
