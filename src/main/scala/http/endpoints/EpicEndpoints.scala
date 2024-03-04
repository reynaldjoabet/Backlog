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
trait EpicEndpoints extends BaseEndpoints {
  private val base = secureBaseEndpoints
    .in("api" / "v1")
    .tag("Epic")

  val post = base.post
    .in("epic")
    .in(jsonBody[CreateEpicRequest])
    .out(jsonBody[Epic]) // Epic

  val list = base.get
    .in("epic")
    .out(jsonBody[List[Epic]])

  def list1[F[_]] = base.get
    .in("epic")
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )

  val get = base.get
    .in("epic")
    .in(path[Long]("id"))
    .out(jsonBody[Epic])

  val delete = base.delete
    .in("epic")
    .in(path[Long]("id"))

}
