package domain

import java.time.Instant

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec

final case class User(
  id: Long, // PK
  email: String,
  hashedPassword: String,
  ctime: Instant = Instant.now(),
  mtime: Instant = Instant.now()
)

object User {
  implicit val userCodec: Codec.AsObject[User] = deriveCodec[User]
}
