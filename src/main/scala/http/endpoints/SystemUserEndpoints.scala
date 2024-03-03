package http.endpoints
import sttp.tapir._
import sttp.tapir.json.circe._
//import sttp.tapir.server.http4s._
import sttp.model.StatusCode
import domain._
import http.requests._
trait SystemUserEndpoints extends BaseEndpoints {
  private val base = secureBaseEndpoints
    .in("api" / "v1")
    .tag("SystemUser")

  val sendEmail = base.post
    .in("sendemail")
    .name("Send Email")

  val list = base.get
    .out(jsonBody[List[SystemUser]])
  val post = base.post
    .in("systemusers") // .in//SystemUser
    .out(jsonBody[SystemUser])
    .in(jsonBody[CreateSystemUserRequest])
  val get = base.get
    .in("systemusers")
    .in(path[Long]("id"))
    .out(jsonBody[SystemUser])
  val delete =
    base.delete
      .in("systemusers")
      .in(path[Long]("id"))

  val put = base.put
    .in("systemusers")
    .in(path[Long]("id"))
    .out(jsonBody[SystemUser])
    .in(jsonBody[CreateSystemUserRequest])
  // .in///SystemUser

}
