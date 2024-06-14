package http
package requests

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class CreateSystemUserRequest(
  systemUserId: Long,
  firstName: String,
  lastName: String,
  password: String,
  emailId: String
)

object CreateSystemUserRequest {

  implicit val codec: Codec.AsObject[CreateSystemUserRequest] =
    deriveCodec[CreateSystemUserRequest]

  implicit val schema: Schema[CreateSystemUserRequest] =
    Schema.derived[CreateSystemUserRequest]

}
