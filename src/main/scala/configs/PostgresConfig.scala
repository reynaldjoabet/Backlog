package configs

import pureconfig.generic.semiauto.deriveReader
import pureconfig.ConfigReader

final case class PostgresConfig(
  host: String,
  port: Int,
  user: String,
  password: String,
  database: String,
  max: Int
)

object PostgresConfig {

  implicit val config: ConfigReader[PostgresConfig] =
    deriveReader[PostgresConfig]

}
