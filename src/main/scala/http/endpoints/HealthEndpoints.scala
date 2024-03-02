package http.endpoints

import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode

trait HealthEndpoints extends BaseEndpoints {

  val healthEndpoint: Endpoint[Unit, Unit, StatusCode, String, Any] =
    baseEndpoint
      .tag("health")
      .name("health")
      .description("Health check")
      .get
      .in("health")
      .out(plainBody[String])

  val errorEndpoint: Endpoint[Unit, Unit, StatusCode, String, Any] =
    baseEndpoint
      .tag("health")
      .name("error health")
      .description("Health check - should fail")
      .get
      .in("health" / "error")
      .out(plainBody[String])

}
