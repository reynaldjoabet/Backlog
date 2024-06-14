package http
package requests

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class CreatePriorityRequest(
  priorityId: Long,
  priority: String
)

object CreatePriorityRequest {

  implicit val codec: Codec.AsObject[CreatePriorityRequest] =
    deriveCodec[CreatePriorityRequest]

  implicit val schema: Schema[CreatePriorityRequest] =
    Schema.derived[CreatePriorityRequest]

}
