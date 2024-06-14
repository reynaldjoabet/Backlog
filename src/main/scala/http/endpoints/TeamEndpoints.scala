package http.endpoints

import java.nio.charset.StandardCharsets

import domain._
import http.requests._
import sttp.capabilities.fs2.Fs2Streams
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe._

trait TeamEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints.in("api" / "v9" / "teams").tag("Teams")

  val delete = base.delete.in(path[Long]("id")).name("Delete Team")

  val get = base.get.in(path[Long]("id")).name("GetTeam by Id ").out(jsonBody[Team])

  val put = base
    .put
    .in(path[Long]("id"))
    .name("Update Team")
    .in(jsonBody[CreateTeamRequest]) // .in//Team
    .out(jsonBody[Team])

  val post = base
    .post
    // .in(path[Long]("id"))
    .name("Create Team").in(jsonBody[CreateTeamRequest]).out(jsonBody[Team])

  val list = base.in("existing-teams").name("Existing Teams").out(jsonBody[List[Team]])

  def list1[F[_]] = base
    .in("existing-teams")
    .name("Existing Teams")
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )

}
