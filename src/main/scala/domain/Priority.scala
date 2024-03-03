package domain
import sttp.tapir.Schema
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
final case class Priority(
    priorityId: Long,
    priority: String
)

object Priority {
  implicit val codec: Codec.AsObject[Priority] = deriveCodec[Priority]
  implicit val schema: sttp.tapir.Schema[domain.Priority] =
    Schema.derived[Priority]
}
