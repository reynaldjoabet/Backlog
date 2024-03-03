package http
package requests
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema
final case class CreateStatusRequest(
    statusId: Long,
    status: String
)

object CreateStatusRequest {
  implicit val codec: Codec.AsObject[CreateStatusRequest] =
    deriveCodec[CreateStatusRequest]
  implicit val schema: sttp.tapir.Schema[CreateStatusRequest] =
    Schema.derived[CreateStatusRequest]
}
