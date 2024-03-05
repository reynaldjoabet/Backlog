package http.routes
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.server.Router

final case class SystemUserRoutes[F[_]: Async](
    systemUserService: SystemUserService[F],
    emailService: EmailService[F]
) extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v1/systemusers"

  val routes = AuthedRoutes.of[User, F] {
    case req @ PUT -> Root as user =>
      req.req.as[CreateSystemUserRequest]
      ???

    case req @ POST -> Root as user =>
      req.req.as[CreateSystemUserRequest]
      ???
    case req @ POST -> Root / "sendemail" as user =>
      ???
    case DELETE -> Root / LongVar(id) as user => ???
    case GET -> Root / LongVar(id) as user    => ???
    case GET -> Root as user                  => ???
  }
}
