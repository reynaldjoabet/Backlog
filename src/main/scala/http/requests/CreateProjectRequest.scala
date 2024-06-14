package http
package requests

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

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

  implicit val schema: Schema[CreateProjectRequest] =
    Schema.derived[CreateProjectRequest]

}
