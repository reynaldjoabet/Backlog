package http.routes
import cats.effect.Async
import org.http4s.dsl.Http4sDsl
import services._
import http.requests._
import domain._
import org.http4s._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.server.Router
final case class DurationRoutes[F[_]: Async](
    durationService: DurationService[F]
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/api/v6"

  val routes = AuthedRoutes.of[User, F] {
    case GET -> Root / "durations" as user => ???
    case req @ POST -> Root / "durations" as user =>
      req.req.as[CreateDurationRequest]
      ???
  }

}
