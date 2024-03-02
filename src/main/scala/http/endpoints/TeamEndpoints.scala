package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait TeamEndpoints extends BaseEndpoints {

  val base = secureBaseEndpoints
    .in("api" / "v9" / "teams")
    .tag("Teams")

  val delete = base.delete
    .in(path[Long]("id"))
    .name("Delete Team")
  val get = base.get
    .in(path[Long]("id"))
    .name("GetTeam by Id ")

  val put = base.put
    .in(path[Long]("id"))
    .name("Update Team") // .in//Team

  val post = base.post
    .in(path[Long]("id"))
    .name("Create Team") // .in//Team

  val list = base.in("existing-teams").name("Existing Teams")
}
