package modules

import cats.effect.kernel._
import skunk.Session
import dev.profunktor.redis4cats.RedisCommands
import services.RedisService
import services.CatsRedisServiceLive._
import cats.effect.IO
import services.CatsRedisServiceLive
import org.typelevel.log4cats.Logger

sealed abstract class Services[F[_]] private (
    val redis: RedisService[F]
    // val cart: ShoppingCart[F],
    // val brands: Brands[F],
    // val categories: Categories[F],
    // val items: Items[F],
    // val orders: Orders[F],
    // val healthCheck: HealthCheck[F]
)

//construct the services  here
object Services {
  // def make[F[_]: Temporal:Logger]
  def make(
      redis: Resource[IO, RedisCommands[IO, String, String]],
      postgres: Resource[IO, Session[IO]]
  ): Services[IO] = {
    new Services[IO](CatsRedisServiceLive(redis)) {}
  }

}

// final class Core[F[_]] private (val jobs: Jobs[F], val users: Users[F], val auth: Auth[F])

// //  postgres -> jobs -> core -> httpApi -> api
// object Core {

//   def apply[F[_]: Async: Logger](
//       xa: Transactor[F],
//       tokenConfig: TokenConfig,
//       emailserviceConfig: EmailServiceConfig
//   ): Resource[F, Core[F]] = {
//     val coreF = for {
//       jobs   <- LiveJobs[F](xa)
//       users  <- LiveUsers[F](xa)
//       tokens <- LiveTokens[F](users)(xa, tokenConfig)
//       emails <- LiveEmails[F](emailserviceConfig)
//       auth   <- LiveAuth[F](users, tokens, emails)
//     } yield new Core(jobs, users, auth)

//     Resource.eval(coreF)
//   }
// }
