package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait ProjectEndpoints extends BaseEndpoints {
  val base = secureBaseEndpoints
    .in("api" / "v7")
    .tag("Projects")

  val post = base.post.in("projects") // inProject

  val list = base.get.in("projects")

  val delete = base.delete.in("projects").in(path[Long]("id"))

  val put = base.delete.in("projects").in(path[Long]("id")) // inProject
}
