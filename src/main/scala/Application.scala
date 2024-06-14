import cats.effect._
import cats.effect.syntax.all
import cats.effect.Trace
import cats.syntax.all._

import configs._
import configs.syntax._
import configs.AppConfig._
import http.controllers.SwaggerDocs
import http.routes.PrometheusMeteredRoute
import io.circe
import modules._
import modules.Services
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.metrics.TerminationType
import org.http4s.HttpRoutes
import pureconfig._
import services.CatsRedisServiceLive
import services.CatsRedisServiceLive._
import sttp.tapir.server.http4s.Http4sServerInterpreter

object Application extends IOApp.Simple {

  override protected def blockedThreadDetectionEnabled = true

  override def run: IO[Unit] =
    ConfigSource
      .default
      .loadF[IO, AppConfig]
      .flatMap {
        case AppConfig(
              postgresConfig,
              emberConfig,
              securityConfig,
              tokenConfig,
              emailServiceConfig,
              stripeConfig,
              redisConfig
            ) =>
          val appResource = for {
            postgres <- Database.makePostgresResource(postgresConfig)
            services <- Services.make(CatsRedisServiceLive.makeRedis(redisConfig), postgres)
            httpApi   = HttpApi.make[IO](services)
            httpApp  <- httpApi.csrfHttpApp // not needed
            combinedRoutes: HttpRoutes[IO] =
              SwaggerDocs.swaggerRoute <+> httpApi.corsHtppRoutes // <+> Http4sServerInterpreter[
            // IO].toRoutes(SwaggerDocs.allEndpoints)

            prometheusMeteredRoutes <- new PrometheusMeteredRoute[IO](
                                         combinedRoutes
                                       ).prometheusMeteredRoutes
            csrfApp <- httpApi.csrfService.map(_(prometheusMeteredRoutes.orNotFound))

            server <- EmberServerBuilder
                        .default[IO]
                        .withHost(emberConfig.host)
                        .withPort(emberConfig.port)
                        .withHttpApp(csrfApp)
                        .build
          } yield server

          appResource.use(_ => IO.println("Server ready!") *> IO.never)
      }

}
