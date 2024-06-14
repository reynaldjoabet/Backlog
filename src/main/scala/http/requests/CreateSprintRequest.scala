package http
package requests

import java.time.LocalDate

import io.circe._
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.semiauto.deriveEncoder
import io.circe.Encoder
import sttp.tapir.Schema

final case class CreateSprintRequest(
  sprintId: Long,
  sprintName: String,
  duration: String,
  startDate: LocalDate,
  endDate: LocalDate,
  sprintGoal: String
)

object CreateSprintRequest {

  implicit val encoder: Encoder.AsObject[CreateSprintRequest] =
    deriveEncoder[CreateSprintRequest]

  implicit val decoder: Decoder[CreateSprintRequest] =
    deriveDecoder[CreateSprintRequest]

  implicit val schema: Schema[CreateSprintRequest] =
    Schema.derived[CreateSprintRequest]

}
