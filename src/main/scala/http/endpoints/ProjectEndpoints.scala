package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import sttp.tapir.json.circe._
import http.requests._
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

  val delete = base.delete
    .in("projects")
    .in(path[Long]("id"))

  val put = base.delete
    .in("projects")
    .in(path[Long]("id"))
    .in(jsonBody[CreateProjectRequest])
    .out(jsonBody[Project]) // inProject
}
