package http.endpoints

import java.nio.charset.StandardCharsets

import domain._
import http.requests._
import sttp.capabilities.fs2.Fs2Streams
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe._

trait PriorityEndpoints extends BaseEndpoints {

  private val base = secureBaseEndpoints.in("api" / "v5").tag("Priority")

  val post = base.post.in("priorities").in(jsonBody[CreatePriorityRequest]).out(jsonBody[Priority])

  val list = base.get.in("priorities").out(jsonBody[List[Priority]])

  def list1[F[_]] = base
    .get
    .in("priorities")
    .out(
      streamTextBody(Fs2Streams[F])(
        CodecFormat.Json(),
        Option(StandardCharsets.UTF_8)
      )
    )

}
