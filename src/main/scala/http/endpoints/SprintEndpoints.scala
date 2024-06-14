package http.endpoints

import java.nio.charset.StandardCharsets

import domain._
import http.requests._
import sttp.capabilities.fs2.Fs2Streams
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe._

trait SprintEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints.in("api" / "v2").tag("Sprints")

  val post = base.post.in("sprints").in(jsonBody[CreateSprintRequest]).out(jsonBody[Sprint])
  // inSprint

  val list = base.get.in("sprints").out(jsonBody[List[Sprint]])

  def list1[F[_]] = base
    .get
    .in("sprints")
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )

  val get = base.get.in("sprints").in(path[Long]("SprintId")).out(jsonBody[Sprint])

  val delete =
    base.delete.in("sprints").in(path[Long]("SprintId"))

  val put =
    base
      .put
      .in("sprints")
      .in(path[Long]("SprintId"))
      .in(jsonBody[CreateSprintRequest])
      .out(jsonBody[Sprint]) // inSprint

}
