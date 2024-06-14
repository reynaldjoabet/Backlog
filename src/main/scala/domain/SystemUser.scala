package domain

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class SystemUser(
  systemUserId: Long,
  firstName: String,
  lastName: String,
  password: String,
  emailId: String
)

object SystemUser {

  implicit val codec: Codec.AsObject[SystemUser] = deriveCodec[SystemUser]

  implicit val schema: Schema[domain.SystemUser] =
    Schema.derived[SystemUser]

}
