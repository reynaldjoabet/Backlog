package domain
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
// Define some roles
 sealed trait Role

 object Role{
    case object Admin extends Role
    case object Moderator extends Role
    case object User extends Role

    implicit val roleCodec: Codec.AsObject[Role]=deriveCodec[Role]
 }
