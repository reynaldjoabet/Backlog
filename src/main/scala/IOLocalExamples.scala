import scala.concurrent.duration._

import cats.effect._

object IOLocalExamples extends IOApp.Simple {

  def inc(name: String, local: IOLocal[Int]): IO[Unit] =
    local.update(_ + 1) >>
      local.get.flatMap(current => IO.println(s"fiber $name: $current"))

  val g = for {
    local   <- IOLocal(42)
    _       <- inc("1", local)
    _       <- inc("2", local)
    current <- local.get
    _       <- IO.println(s"fiber A: $current")
  } yield ()

// output:
  // update 1: 43
  // update 2: 44
  // fiber A: 44
  override def run: IO[Unit] = m >> i // f//g >>f

  def update(name: String, local: IOLocal[Int], f: Int => Int): IO[Unit] =
    local.update(f) >> local.get.flatMap(current => IO.println(s"$name: $current"))

  val f = for {
    local   <- IOLocal(42)
    fiber1  <- update("fiber B", local, _ - 1).start
    fiber2  <- update("fiber C", local, _ + 1).start
    _       <- fiber1.joinWithNever
    _       <- fiber2.joinWithNever
    current <- local.get
    _       <- IO.println(s"fiber A: $current")
  } yield ()

  // output:
  // fiber B: 41
  // fiber C: 43
  // fiber A: 42

  def update2(name: String, local: IOLocal[Int], f: Int => Int): IO[Unit] =
    IO.sleep(1.second) >> local
      .update(f) >> local.get.flatMap(current => IO.println(s"$name: $current"))

  val i = for {
    local  <- IOLocal(42)
    fiber1 <- update2("fiber B", local, _ + 1).start
    fiber2 <- update2("fiber C", local, _ + 2).start
    _      <- fiber1.joinWithNever
    _      <- fiber2.joinWithNever
    _      <- update("fiber A", local, _ - 1)
  } yield ()

  //   fiber C: 44
  //   fiber B: 43
  //   fiber A: 41

  import cats.effect.{IO, IOLocal, Resource, Sync}
  import cats.effect.std.{Console, Random}
  import cats.syntax.flatMap._
  import cats.syntax.functor._
  import cats.Monad

  case class TraceId(value: String)

  object TraceId {

    def gen[F[_]: Sync]: F[TraceId] =
      Random.scalaUtilRandom[F].flatMap(_.nextString(8)).map(TraceId(_))

  }

  trait TraceIdScope[F[_]] {

    def get: F[TraceId]
    def scope(traceId: TraceId): Resource[F, Unit]

  }

  object TraceIdScope {

    def apply[F[_]](implicit ev: TraceIdScope[F]): TraceIdScope[F] = ev

    def fromIOLocal: IO[TraceIdScope[IO]] =
      for {
        local <- IOLocal(TraceId("global"))
      } yield new TraceIdScope[IO] {

        def get: IO[TraceId] =
          local.get

        def scope(traceId: TraceId): Resource[IO, Unit] =
          Resource.make(local.getAndSet(traceId))(previous => local.set(previous)).void

      }

  }

  def service[F[_]: Sync: Console: TraceIdScope]: F[String] =
    for {
      traceId <- TraceId.gen[F]
      result  <- TraceIdScope[F].scope(traceId).use(_ => callRemote[F])
    } yield result

  def callRemote[F[_]: Monad: Console: TraceIdScope]: F[String] =
    for {
      traceId <- TraceIdScope[F].get
      _       <- Console[F].println(s"Processing request. TraceId: $traceId")
    } yield "some response"

  val m = TraceIdScope
    .fromIOLocal
    .flatMap { implicit traceIdScope: TraceIdScope[IO] =>
      service[IO]
    }

}
