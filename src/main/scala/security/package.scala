import cats.syntax.applicative._
import cats.syntax.functor._
import cats.Applicative
import fs2.{text, Chunk, Pipe, Pull, Stream}

package object security {

  def arrange[F[_], O](
    f: (Chunk[O], Chunk[O]) => (Chunk[O], Chunk[O])
  ): Pipe[F, O, O] =
    _.pull.scanChunks(Chunk.empty[O])(f).flatMap(Pull.output(_).void).stream

  def chunkTimesN[F[_], O](n: Int): Pipe[F, O, O] = {
    assert(n > 0, s"n($n) must be positive!")
    arrange { (acc, hd) =>
      val headSize = hd.size
      val size     = acc.size + headSize
      if (size < n) (acc ++ hd, Chunk.empty[O])
      else if (size % n == 0) (Chunk.empty[O], acc ++ hd)
      else {
        val (pfx, sfx) = hd.splitAt(headSize - size % n)
        (sfx, acc ++ pfx)
      }
    }
  }

  def scanChunksLast[F[_], I, I2 >: I, O, S](init: => S)(
    f: (S, Chunk[I2]) => (S, Chunk[O])
  )(last: (S, Chunk[I2]) => Chunk[O]): Pipe[F, I, O] = {
    _.pull
      .scanChunks((init, Chunk.empty[I])) { (stateAccTuple, hd) =>
        val (current, acc) = stateAccTuple
        if (acc.isEmpty) ((current, hd), Chunk.empty[O])
        else {
          val (next, os) = f.tupled(stateAccTuple)
          ((next, hd), os)
        }
      }
      .flatMap(stateAccTuple => Pull.output(last.tupled(stateAccTuple)).void)
      .stream
  }

  def mapChunkLast[F[_], I, I2 >: I, O](
    f: Chunk[I2] => Chunk[O]
  )(last: Chunk[I2] => Chunk[O]): Pipe[F, I, O] =
    _.pull
      .scanChunks(Chunk.empty[I])((acc, hd) => (hd, f(acc)))
      .flatMap[F, O, Unit](acc => Pull.output(last(acc)).void)
      .stream

  private[this] def evalScanChunksOptPull[
    F[_],
    F2[x] >: F[x],
    I,
    I2 >: I,
    O,
    S
  ](acc: S, s: Stream[F, I])(
    f: S => Option[Chunk[I2] => F2[(S, Chunk[O])]]
  ): Pull[F2, O, S] =
    f(acc) match {
      case None => Pull.pure(acc)
      case Some(g) =>
        s.pull
          .uncons
          .flatMap {
            case Some((hd, tl)) =>
              Pull
                .eval(g(hd))
                .flatMap(stateAccTuple =>
                  Pull.output(stateAccTuple._2) >> evalScanChunksOptPull[
                    F,
                    F2,
                    I,
                    I2,
                    O,
                    S
                  ](
                    stateAccTuple._1,
                    tl
                  )(f)
                )
            case None => Pull.pure(acc)
          }
    }

  def evalScanChunksOpt[F[_], F2[x] >: F[x], I, I2 >: I, O, S](init: => S)(
    f: S => Option[Chunk[I2] => F2[(S, Chunk[O])]]
  ): Stream[F, I] => Stream[F2, O] =
    in => evalScanChunksOptPull[F, F2, I, I2, O, S](init, in)(f).void.stream

  def evalScanChunks[F[_], F2[x] >: F[x], I, I2 >: I, O, S](init: => S)(
    f: (S, Chunk[I2]) => F2[(S, Chunk[O])]
  ): Stream[F, I] => Stream[F2, O] =
    evalScanChunksOpt[F, F2, I, I2, O, S](init)(s => Some(c => f(s, c)))

  def evalScanChunksLast[F[_], F2[x] >: F[x]: Applicative, I, I2 >: I, O, S](
    init: => S
  )(
    f: (S, Chunk[I2]) => F2[(S, Chunk[O])]
  )(last: (S, Chunk[I2]) => F2[Chunk[O]]): Stream[F, I] => Stream[F2, O] =
    in => {
      evalScanChunksOptPull[F, F2, I, I2, O, (S, Chunk[I2])](
        (init, Chunk.empty[I2]),
        in
      )(stateAccTuple =>
        Some { hd =>
          val (current, acc) = stateAccTuple
          if (acc.isEmpty) Applicative[F2].pure(((current, hd), Chunk.empty[O]))
          else {
            f(current, acc).map { nextOsTuple =>
              val (next, os) = nextOsTuple
              ((next, hd), os)
            }
          }
        }
      ).flatMap(stateAccTuple => Pull.eval(last.tupled(stateAccTuple)).flatMap(Pull.output(_).void))
        .stream
    }

  def evalMapChunk[F[_], F2[x] >: F[x], I, I2 >: I, O](
    f: Chunk[I2] => F2[Chunk[O]]
  ): Stream[F, I] => Stream[F2, O] =
    in => in.chunks.flatMap(o => Stream.evalUnChunk(f(o)))

  def evalMapChunkLast[F[_], F2[x] >: F[x], I, I2 >: I, O](
    f: Chunk[I2] => F2[Chunk[O]]
  )(last: Chunk[I2] => F2[Chunk[O]]): Stream[F, I] => Stream[F2, O] = {
    def go(acc: Chunk[I], s: Stream[F, I]): Pull[F2, O, Unit] =
      s.pull
        .uncons
        .flatMap {
          case Some((hd, tl)) =>
            Pull.eval(f(acc)).flatMap(os => Pull.output(os) >> go(hd, tl))
          case None =>
            Pull.eval(last(acc)).flatMap(os => Pull.output(os) >> Pull.pure(()))
        }
    in => go(Chunk.empty[I], in).stream
  }

  implicit class StreamOps[F[_], I](stream: Stream[F, I]) {

    def arrange(f: (Chunk[I], Chunk[I]) => (Chunk[I], Chunk[I])): Stream[F, I] =
      stream.through(security.arrange(f))

    def chunkTimesN(n: Int): Stream[F, I] =
      stream.through(security.chunkTimesN(n))

    def scanChunksLast[I2 >: I, O, S](init: => S)(
      f: (S, Chunk[I2]) => (S, Chunk[O])
    )(last: (S, Chunk[I2]) => Chunk[O]): Stream[F, O] =
      stream.through(security.scanChunksLast[F, I, I2, O, S](init)(f)(last))

    def mapChunkLast[I2 >: I, O](f: Chunk[I2] => Chunk[O])(
      last: Chunk[I2] => Chunk[O]
    ): Stream[F, O] =
      stream.through(security.mapChunkLast[F, I, I2, O](f)(last))

    def evalScanChunksOpt[F2[x] >: F[x], I2 >: I, O, S](init: => S)(
      f: S => Option[Chunk[I2] => F2[(S, Chunk[O])]]
    ): Stream[F2, O] =
      stream.through(security.evalScanChunksOpt[F, F2, I, I2, O, S](init)(f))

    def evalScanChunks[F2[x] >: F[x], I2 >: I, O, S](init: => S)(
      f: (S, Chunk[I2]) => F2[(S, Chunk[O])]
    ): Stream[F2, O] =
      stream.through(security.evalScanChunks[F, F2, I, I2, O, S](init)(f))

    def evalScanChunksLast[F2[x] >: F[x]: Applicative, I2 >: I, O, S](
      init: => S
    )(
      f: (S, Chunk[I2]) => F2[(S, Chunk[O])]
    )(last: (S, Chunk[I2]) => F2[Chunk[O]]): Stream[F2, O] =
      stream.through(
        security.evalScanChunksLast[F, F2, I, I2, O, S](init)(f)(last)
      )

    def evalMapChunk[F2[x] >: F[x], I2 >: I, O](
      f: Chunk[I2] => F2[Chunk[O]]
    ): Stream[F2, O] =
      stream.through(security.evalMapChunk[F, F2, I, I2, O](f))

    def evalMapChunkLast[F2[x] >: F[x], I2 >: I, O](
      f: Chunk[I2] => F2[Chunk[O]]
    )(last: Chunk[I2] => F2[Chunk[O]]): Stream[F2, O] =
      stream.through(security.evalMapChunkLast[F, F2, I, I2, O](f)(last))

  }

  implicit class ByteOps(bytes: Array[Byte]) {

    def hex: String = Stream.chunk(Chunk.array(bytes)).through(text.hex.encode).toList.mkString("")

    def utf8: String = Stream
      .chunk(Chunk.array(bytes))
      .through(text.utf8.decode)
      .toList
      .mkString("")

  }

}
