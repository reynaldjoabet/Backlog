package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode

trait BaseEndpoints {

  // Endpoint[Unit, Unit, Throwable, Unit, Any]
  val baseEndpoint: Endpoint[Unit, Unit, StatusCode, Unit, Any] =
    endpoint
      .errorOut(statusCode)
  // .errorOut(statusCode and plainBody[String])
  // .mapErrorOut[Throwable](HttpError.decode(_))(HttpError.encode)

  // Endpoint[String, Unit, Throwable, Unit, Any]
  val secureBaseEndpoints: Endpoint[String, Unit, StatusCode, Unit, Any] =
    baseEndpoint
      .securityIn(auth.bearer[String]())

}
