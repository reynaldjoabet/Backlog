package http
package requests

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class CreateIssueTypeRequest(
  issuetypeId: Long,
  issuetypeName: String
)

object CreateIssueTypeRequest {

  implicit val codec: Codec.AsObject[CreateIssueTypeRequest] =
    deriveCodec[CreateIssueTypeRequest]

  implicit val schema: Schema[CreateIssueTypeRequest] =
    Schema.derived[CreateIssueTypeRequest]

}
