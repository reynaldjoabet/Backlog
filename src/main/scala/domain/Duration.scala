package domain

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class Duration(durationId: Long, duraton: String)

object Duration {

  implicit val codec: Codec.AsObject[Duration] = deriveCodec[Duration]
  implicit val schema: Schema[Duration]        = Schema.derived[Duration]

}
