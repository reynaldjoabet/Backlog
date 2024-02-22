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
object Main extends IOApp.Simple {
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
          services = Services.make(CatsRedisServiceLive.resource, postgres)
          httpApp <- HttpApi.make[IO](services).crsfHttpApp
          server <- EmberServerBuilder
            .default[IO]
            .withHost(emberConfig.host)
            .withPort(emberConfig.port)
            .withHttpApp(httpApp)
            .build
        } yield server

        appResource.use(_ => IO.println("Server ready!") *> IO.never)
    }
}
