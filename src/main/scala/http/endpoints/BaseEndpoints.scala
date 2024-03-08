package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode

trait BaseEndpoints {

  // Endpoint[Unit, Unit, Throwable, Unit, Any]
  protected val baseEndpoint: Endpoint[Unit, Unit, StatusCode, Unit, Any] =
    endpoint
      .errorOut(statusCode)
  // .errorOut(statusCode and plainBody[String])
  // .mapErrorOut[Throwable](HttpError.decode(_))(HttpError.encode)

  // Endpoint[String, Unit, Throwable, Unit, Any]
  protected val secureBaseEndpoints
      : Endpoint[String, Unit, StatusCode, Unit, Any] =
    baseEndpoint
      .securityIn(auth.bearer[String]())

final case class MyCookie(value:String)
      //auth.http()
  val g = endpoint
    .in(header[String]("X-Csrf-Token"))
    .in(cookie[String]("csrf-Token"))
    .in(cookie[String]("SessionID").map[MyCookie](MyCookie)(_.value))
}
