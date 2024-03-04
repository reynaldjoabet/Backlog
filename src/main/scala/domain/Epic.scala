package domain
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema
final case class Epic(
    id: Long,
    project: String,
    epicName: String,
    epicSummary: String,
    reporter: String,
    epicDescription: String,
    priority: String,
    assignee: String,
    team: String,
    targetStart: String,
    targetEnd: String,
    storyPoints: String
)

object Epic {
  implicit val codec: Codec.AsObject[Epic] = deriveCodec[Epic]
  implicit val schema: Schema[domain.Epic] = Schema.derived[Epic]
}
