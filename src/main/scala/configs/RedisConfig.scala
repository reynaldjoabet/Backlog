package configs

import pureconfig.generic.semiauto.deriveReader
import pureconfig.ConfigReader

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
