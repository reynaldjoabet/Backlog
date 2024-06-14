package http.endpoints

import java.nio.charset.StandardCharsets

import domain._
import http.requests._
import sttp.capabilities.fs2.Fs2Streams
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe._

trait ProjectEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints.in("api" / "v7").tag("Projects")

  val post = base.post.in("projects").in(jsonBody[CreateProjectRequest]).out(jsonBody[Project]) // inProject

  val list = base.get.in("projects").out(jsonBody[List[Project]])

  def list1[F[_]] = base
    .get
    .in("projects")
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )

  val delete = base.delete.in("projects").in(path[Long]("id"))

  val put = base
    .delete
    .in("projects")
    .in(path[Long]("id"))
    .in(jsonBody[CreateProjectRequest])
    .out(jsonBody[Project]) // inProject

}
