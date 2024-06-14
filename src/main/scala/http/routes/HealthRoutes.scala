package http.routes

//import cats.effect.syntax.all._
import scala.util.Random

import cats.effect.Async
import cats.syntax.all._

import domain._
import http.responses.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityEncoder.circeEntityEncoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import org.http4s.Response

final case class HealthRoutes[F[_]: Async](
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/healthcheck"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    Ok(new UserID(9, "", Set.empty[Role]))
      .map(_.createCookie(Random.alphanumeric.take(120).mkString))
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )

}
