package http
package requests
import sttp.tapir.Schema
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
final case class CreatePriorityRequest(
    priorityId: Long,
    priority: String
)

object CreatePriorityRequest {
  implicit val codec: Codec.AsObject[CreatePriorityRequest] =
    deriveCodec[CreatePriorityRequest]
  implicit val schema: sttp.tapir.Schema[CreatePriorityRequest] =
    Schema.derived[CreatePriorityRequest]
}
