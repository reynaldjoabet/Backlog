package domain

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class Priority(
  priorityId: Long,
  priority: String
)

object Priority {

  implicit val codec: Codec.AsObject[Priority] = deriveCodec[Priority]

  implicit val schema: Schema[domain.Priority] =
    Schema.derived[Priority]

}
