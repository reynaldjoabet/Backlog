package domain
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
final case class UserID(id: Long, email: String,roles:Set[Role])

object UserID {
  implicit val userIdCodec: Codec.AsObject[UserID] = deriveCodec[UserID]
}
