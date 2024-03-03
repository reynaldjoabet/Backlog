package http
package requests
import sttp.tapir.Schema
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.Encoder
import io.circe._
import java.time.LocalDate
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
  implicit val schema: sttp.tapir.Schema[CreateSprintRequest] =
    Schema.derived[CreateSprintRequest]

}
