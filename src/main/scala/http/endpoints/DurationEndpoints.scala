package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import sttp.tapir.json.circe._
import domain._
import http.requests._
import sttp.capabilities.fs2.Fs2Streams
import java.nio.charset.StandardCharsets
trait DurationEndpoints extends BaseEndpoints {
  private val base = secureBaseEndpoints
    .in("api" / "v6")
    .tag("Durations")

  val post = base.post
    .in("durations")
    .in(jsonBody[CreateDurationRequest])
    .out(jsonBody[Duration].example(new Duration(1L, "30")))

  val list = base.get
    .in("durations")
    .out(jsonBody[List[Duration]])

  def list1[F[_]] = base.get
    .in("durations")
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )
}
