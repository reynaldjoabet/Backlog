package domain
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema
final case class Team(
    id: Long,
    teamName: String,
    users: Int,
    members: String
)

object Team {
  implicit val codec: Codec.AsObject[Team] = deriveCodec[Team]
  implicit val schema: Schema[domain.Team] = Schema.derived[Team]
}
