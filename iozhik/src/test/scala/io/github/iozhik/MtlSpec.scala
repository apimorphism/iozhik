package io.github.iozhik

import cats.{Monad, MonadError}
import cats.data.StateT
import cats.mtl.MonadState

import cats.implicits._
import cats.mtl.implicits._

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

case class MState(value: Int)

class MtlSpec extends AnyFlatSpec with Matchers {

  "boo" should "foo" in {

    def createProgram[F[_]: Monad](implicit S: MonadState[F, MState], E: MonadError[F, String]): F[Int] = {
      for {
        s <- S.get
        _ <- S.set(s.copy(value = s.value + 1))
      } yield {
        s.value + 1
      }
    }

    val program = createProgram[StateT[Either[String, *], MState, *]]

    for {
      res <- program.run(MState(2))
      (s, v) = res
    } yield {
      println(s)
      println(v)
      v
    }
  }

}
