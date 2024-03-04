package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import sttp.tapir.json.circe._
import http.requests._
import sttp.capabilities.fs2.Fs2Streams
import java.nio.charset.StandardCharsets
trait ProjectEndpoints extends BaseEndpoints {
  private val base = secureBaseEndpoints
    .in("api" / "v7")
    .tag("Projects")

  val post = base.post
    .in("projects")
    .in(jsonBody[CreateProjectRequest])
    .out(jsonBody[Project]) // inProject

  val list = base.get
    .in("projects")
    .out(jsonBody[List[Project]])

  def list1[F[_]] = base.get
    .in("projects")
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )

  val delete = base.delete
    .in("projects")
    .in(path[Long]("id"))

  val put = base.delete
    .in("projects")
    .in(path[Long]("id"))
    .in(jsonBody[CreateProjectRequest])
    .out(jsonBody[Project]) // inProject
}
