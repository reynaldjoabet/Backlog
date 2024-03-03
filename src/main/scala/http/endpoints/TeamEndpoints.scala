package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import http.requests._
trait TeamEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints
    .in("api" / "v9" / "teams")
    .tag("Teams")

  val delete = base.delete
    .in(path[Long]("id"))
    .name("Delete Team")

  val get = base.get
    .in(path[Long]("id"))
    .name("GetTeam by Id ")
    .out(jsonBody[Team])
  val put = base.put
    .in(path[Long]("id"))
    .name("Update Team")
    .in(jsonBody[CreateTeamRequest]) // .in//Team
    .out(jsonBody[Team])
  val post = base.post
    .in(path[Long]("id"))
    .name("Create Team")
    .in(jsonBody[CreateTeamRequest])
    .out(jsonBody[Team])

  val list = base
    .in("existing-teams")
    .name("Existing Teams")
    .out(jsonBody[List[Team]])
}
