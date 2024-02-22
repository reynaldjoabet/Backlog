package configs
import pureconfig.ConfigReader
import pureconfig.generic.semiauto.deriveReader

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
