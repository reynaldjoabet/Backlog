package http.routes
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.server.Router

final case class EpicRoutes[F[_]: Async](epicService: EpicService[F])
    extends Http4sDsl[F] {
  private[routes] val prefixPath = "/api/v1/"

  val routes = AuthedRoutes.of[User, F] {
    case GET -> Root / "epic" / LongVar(id) as user => ???

    case DELETE -> Root / "epic" / LongVar(id) as user => ???

    case GET -> Root / "epic" as user => ??? // list

    case req @ POST -> Root / "epic" as user =>
      req.req.as[CreateEpicRequest]
      ???
  }
}
