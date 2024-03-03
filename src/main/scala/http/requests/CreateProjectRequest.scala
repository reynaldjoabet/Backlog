package http
package requests
import sttp.tapir.Schema
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
final case class CreateProjectRequest(
    id: Long,
    projectName: String,
    imageURL: String,
    description: String,
    client: String,
    projectLead: String
)

object CreateProjectRequest {
  implicit val codec: Codec.AsObject[CreateProjectRequest] =
    deriveCodec[CreateProjectRequest]
  implicit val schema: sttp.tapir.Schema[CreateProjectRequest] =
    Schema.derived[CreateProjectRequest]
}
