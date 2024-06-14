package domain

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class IssueType(
  issuetypeId: Long,
  issuetypeName: String
)

object IssueType {

  implicit val codec: Codec.AsObject[IssueType] = deriveCodec[IssueType]

  implicit val schema: Schema[domain.IssueType] =
    Schema.derived[IssueType]

}
