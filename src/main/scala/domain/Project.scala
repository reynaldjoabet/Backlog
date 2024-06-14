package domain

import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
import sttp.tapir.Schema

final case class Project(
  id: Long,
  projectName: String,
  imageURL: String,
  description: String,
  client: String,
  projectLead: String
)

object Project {

  implicit val codec: Codec.AsObject[Project] = deriveCodec[Project]

  implicit val schema: Schema[domain.Project] =
    Schema.derived[Project]

}
