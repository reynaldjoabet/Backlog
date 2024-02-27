package http.requests
import org.http4s.metrics.prometheus.{Prometheus, PrometheusExportService}
import org.http4s.server.Router
import org.http4s.server.middleware.Metrics
import cats.effect._
import org.http4s.HttpRoutes
class PrometheusMeteredRoute[F[_]:Sync](apiService:HttpRoutes[F]) {

  val prometheusMeteredRouter: Resource[F, HttpRoutes[F]] =
     for{
       metricsSvc <- PrometheusExportService.build[F]
       metrics <- Prometheus.metricsOps[F](metricsSvc.collectorRegistry, "server")
       router = Router[F](
         "/api" -> Metrics[F](metrics)(apiService),
         "/" -> metricsSvc.routes
       )
     } yield router
}
