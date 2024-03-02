package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait IssuetypeEndpoints extends BaseEndpoints {
  val base = secureBaseEndpoints
    .in("api" / "v4")
    .tag("IssueType") // Issuetype

  val post = base.post.in("issuetypes")

  val list = base.get.in("issuetypes")

  val get = base.get.in("issuetypes").in(path[Long]("IssuetypeId"))
}
