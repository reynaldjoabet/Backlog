package http.endpoints

import java.nio.charset.StandardCharsets

import domain._
import http.requests._
import sttp.capabilities.fs2.Fs2Streams
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe._

trait StatusEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints.in("api" / "v8").tag("Status")

  val post = base.post.in("states").in(jsonBody[CreateStatusRequest]).out(jsonBody[Status])
  // .in Status

  val list = base.get.in("states").out(jsonBody[List[Status]])

  def list1[F[_]] = base
    .get
    .in("states")
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )

}
