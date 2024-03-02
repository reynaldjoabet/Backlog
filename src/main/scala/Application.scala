import pureconfig._
import configs.AppConfig._
import configs._
import cats.effect._
import configs.syntax._
import modules.Services
import org.http4s.ember.server.EmberServerBuilder
import services.CatsRedisServiceLive._
import services.CatsRedisServiceLive
import modules._
import io.circe
import http.routes.PrometheusMeteredRoute
import http.controllers.SwaggerDocs
import cats.syntax.all._
import cats.effect.syntax.all
import sttp.tapir.server.http4s.Http4sServerInterpreter
import org.http4s.HttpRoutes

object Application extends IOApp.Simple {
  override def run: IO[Unit] =
    ConfigSource.default.loadF[IO, AppConfig].flatMap {
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
          services = Services
            .make(CatsRedisServiceLive.makeRedis(redisConfig), postgres)
          httpApi = HttpApi.make[IO](services)
          httpAp <- httpApi.crsfHttpApp
          combinedRoutes: HttpRoutes[IO] =
            SwaggerDocs.swaggerRoute <+> httpApi.corsHtppRoutes <+> Http4sServerInterpreter[
              IO
            ].toRoutes(SwaggerDocs.allEndpoints)

          prometheusMeteredRoutes <- new PrometheusMeteredRoute[IO](
            combinedRoutes
          ).prometheusMeteredRoutes
          csrfApp <- httpApi.csrfService
            .map(_(prometheusMeteredRoutes.orNotFound))

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
