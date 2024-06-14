package domain

import java.time.LocalDate

import io.circe._
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.semiauto.deriveEncoder
import io.circe.Encoder
import sttp.tapir.Schema

final case class Sprint(
  sprintId: Long,
  sprintName: String,
  duration: String,
  startDate: LocalDate,
  endDate: LocalDate,
  sprintGoal: String
)

object Sprint {

  implicit val encoder: Encoder.AsObject[Sprint] = deriveEncoder[Sprint]
  implicit val decoder: Decoder[Sprint]          = deriveDecoder[Sprint]
  implicit val schema: Schema[domain.Sprint]     = Schema.derived[Sprint]

}
