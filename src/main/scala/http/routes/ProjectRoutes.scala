package http.routes
import cats.Monad
import org.http4s.dsl.Http4sDsl

final case class ProjectRoutes[F[_]: Monad](
) extends Http4sDsl[F] {
  
}
