package http
package requests
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema
final case class CreateEpicRequest(
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

object CreateEpicRequest {
  implicit val codec: Codec.AsObject[CreateEpicRequest] =
    deriveCodec[CreateEpicRequest]
  implicit val schema: Schema[CreateEpicRequest] =
    Schema.derived[CreateEpicRequest]
}
