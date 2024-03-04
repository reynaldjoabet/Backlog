package domain
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema
final case class Status(
    statusId: Long,
    status: String
)

object Status {
  implicit val codec: Codec.AsObject[Status] = deriveCodec[Status]
  implicit val schema: Schema[domain.Status] = Schema.derived[Status]
}
