package configs
import pureconfig.ConfigReader
import pureconfig.generic.semiauto.deriveReader
case class RedisConfiguration(
    host: String,
    port: Int,
    password: String,
    database: Int
)

object RedisConfiguration {

  implicit val redisConfigReader: ConfigReader[RedisConfiguration] =
    deriveReader[RedisConfiguration]
}
