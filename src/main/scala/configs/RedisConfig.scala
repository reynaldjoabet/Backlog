package configs
import pureconfig.ConfigReader
import pureconfig.generic.semiauto.deriveReader
case class RedisConfig(
    host: String,
    port: Int,
    password: String,
    database: Int
)

object RedisConfig {

  implicit val redisConfigReader: ConfigReader[RedisConfig] =
    deriveReader[RedisConfig]
}
