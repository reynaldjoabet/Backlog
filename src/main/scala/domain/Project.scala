package domain
import sttp.tapir.Schema
import io.circe.generic.semiauto.deriveCodec
import io.circe.Codec
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
  implicit val schema: sttp.tapir.Schema[domain.Project] =
    Schema.derived[Project]
}
