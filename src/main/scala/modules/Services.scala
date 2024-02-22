package modules

import cats.effect.kernel._
import skunk.Session
import dev.profunktor.redis4cats.RedisCommands
import services.RedisService
import services.CatsRedisServiceLive._
import cats.effect.IO
import services.CatsRedisServiceLive

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
  // def make[F[_]: Temporal]
  def make(
      redis: Resource[IO, RedisCommands[IO, String, String]],
      postgres: Resource[IO, Session[IO]]
      //  cartExpiration: ShoppingCartExpiration
  ): Services[IO] = {
    new Services[IO](CatsRedisServiceLive(CatsRedisServiceLive.resource)) {}
  }

}
