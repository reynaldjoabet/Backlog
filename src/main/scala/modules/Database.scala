package modules

import cats.effect._

import configs._
import org.typelevel.otel4s.trace.Tracer.Implicits.noop
import skunk.Session
import skunk.SessionPool
//import natchez.Trace.Implicits.noop

object Database {

  def makePostgresResource(pc: PostgresConfig): SessionPool[IO] =
    Session.pooled[IO](
      host = pc.host,
      port = pc.port,
      user = pc.user,
      password = Some(pc.password),
      database = pc.database,
      max = pc.max
    )

}
