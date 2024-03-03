package http.endpoints

import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import sttp.tapir.json.circe._
trait HealthEndpoints extends BaseEndpoints {
  private val base = baseEndpoint
    .tag("Health")
  val healthEndpoint: Endpoint[Unit, Unit, StatusCode, String, Any] =
    base
      .description("Health check")
      .name("health")
      .get
      .in("health")
      .out(plainBody[String])

  val errorEndpoint: Endpoint[Unit, Unit, StatusCode, String, Any] =
    base
      .name("error health")
      .description("Health check - should fail")
      .get
      .in("health" / "error")
      .out(plainBody[String])

}
