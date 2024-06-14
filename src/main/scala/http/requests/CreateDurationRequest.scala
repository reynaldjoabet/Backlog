package http
package requests

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class CreateDurationRequest(durationId: Long, duraton: String)

object CreateDurationRequest {

  implicit val codec: Codec.AsObject[CreateDurationRequest] =
    deriveCodec[CreateDurationRequest]

  implicit val schema: Schema[CreateDurationRequest] =
    Schema.derived[CreateDurationRequest]

}
