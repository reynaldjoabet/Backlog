package security.codec

import cats.effect.{Concurrent, IO, IOApp}
import cats.effect.std.Console
import cats.syntax.flatMap._
import cats.syntax.functor._
import fs2.{RaiseThrowable, Stream}
import fs2.{hash, Pipe, Pure, Stream}
import fs2.text.base64

object Base64App extends IOApp.Simple {

  def demo[F[_]: Concurrent: RaiseThrowable: Console](input: Int*): F[Unit] = {
    val b64encodedStream =
      Stream(input: _*).map(_.toByte).through(base64.encode)
    val b64decodedStream = b64encodedStream.covary[F].through(base64.decode)
    for {
      _ <- Console[F].println(
             s" -- input: ${input.map(_.toByte).mkString("[", ", ", "]")}"
           )
      _ <- Console[F].println(
             s"encoded: ${b64encodedStream.toList.mkString("")}"
           )
      decodedList <- b64decodedStream.compile.toList
      _ <- Console[F].println(
             s"decoded: ${decodedList.mkString("[", ", ", "]")}"
           )
    } yield ()
  }

  val run: IO[Unit] =
    for {
      _ <- demo[IO](0xe4, 0xb8, 0xad)
      _ <- demo[IO](0x01, 0x02, 0x7f, 0x00)
      _ <- demo[IO](0xff, 0xa3, 0x98, 0x01, 0x02, 0x7f, 0x00)
      _ <- demo[IO](0xa3, 0x98, 0x01, 0x02, 0x7f, 0x00)
      _ <- demo[IO](0x98, 0x01, 0x02, 0x7f, 0x00)
      _ <- demo[IO](0x01, 0x02, 0x7f, 0x00)
      _ <- demo[IO](0x02, 0x7f, 0x00)
      _ <- demo[IO](0x7f, 0x00)
      _ <- demo[IO](0x00)
    } yield ()

}
