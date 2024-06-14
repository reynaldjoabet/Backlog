package http
package requests

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class CreateTeamRequest(
  id: Long,
  teamName: String,
  users: Int,
  members: String
)

object CreateTeamRequest {

  implicit val codec: Codec.AsObject[CreateTeamRequest] =
    deriveCodec[CreateTeamRequest]

  implicit val schema: Schema[CreateTeamRequest] =
    Schema.derived[CreateTeamRequest]

}
