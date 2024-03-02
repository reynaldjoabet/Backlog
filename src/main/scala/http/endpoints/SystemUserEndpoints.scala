package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
trait SystemUserEndpoints extends BaseEndpoints {
  val base = secureBaseEndpoints
    .in("api" / "v1")
    .tag("SystemUser")

  val sendEmail = base.post
    .in("sendemail")
    .name("Send Email")

  val list = base.get
  val post = base.post.in("systemusers") // .in//SystemUser

  val get = base.get.in("systemusers").in(path[Long]("id"))

  val delete =
    base.delete.in("systemusers").in(path[Long]("id"))

  val put = base.put.in("systemusers").in(path[Long]("id"))
  // .in///SystemUser

}
