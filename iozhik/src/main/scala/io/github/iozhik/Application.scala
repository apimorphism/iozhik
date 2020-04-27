package io.github.iozhik

import java.io.File
import java.nio.file.{Path, Paths}

import cats.Monad
import cats.syntax.functor._
import cats.syntax.flatMap._
import cats.syntax.traverse._
import cats.instances.list._
import cats.effect._
import fs2.{RaiseThrowable, Stream}
import org.scalafmt.interfaces.Scalafmt

import scala.concurrent.ExecutionContext


object Application extends IOApp {

  def generate[F[_]: RaiseThrowable : ContextShift](apiDir: Path, dstDir: Path)(implicit sync: Sync[F]): F[Unit] = {

    val generator = new ScalaApiGeneratorV1
    val formatter = Scalafmt.create(this.getClass.getClassLoader)
    val scalaFmtConfig = apiDir.resolve(".scalafmt.conf")

    Stream.resource(mkPool)
      .flatMap{ implicit ec =>
        readAll(apiDir)
          .map{ code =>
            val r = Parser.Grammar
              .parse(code)
              .flatMap(Generator.Model.translate)
              .flatMap(generator.gen)
            r
          }
          .map(_.left.map(new RuntimeException(_)))
          .flatMap(Stream.fromEither(_))
          .flatMap(Stream.emits(_))
          .evalMap(code => sync.delay{
            val path = dstDir.resolve(code.path)
            val file = path.resolve(code.name)
            path.toFile.mkdirs()
            val body = formatter.format(scalaFmtConfig, file, code.body)
            (file, body)
          })
          .flatMap { case (file, body) =>
            Stream
              .emit(body)
              .through(fs2.text.utf8Encode)
              .to(fs2.io.file.writeAll(file, ec))
          }
          .handleErrorWith(e => Stream.eval(sync.delay(e.printStackTrace())))
      }
      .compile
      .drain
  }

  private def readAll[F[_]: RaiseThrowable : ContextShift](dir: Path)(
    implicit
    ec: ExecutionContext,
    sync: Sync[F]
  ): Stream[F, String] = {
    Stream.eval(sync.delay(Option(dir.toFile.listFiles)))
      .flatMap{
        _.fold[Stream[F, File]](
          Stream.raiseError(new RuntimeException("No such directory: " + dir))
        )(
          Stream.apply(_: _*)
        )
      }
      .map(_.toPath)
      .filter(_.toString.endsWith(".api"))
      .flatMap { f =>
        fs2.io.file
          .readAll[F](f, ec, 4096)
          .through(fs2.text.utf8Decode)
          .reduce(_ + _)
      }
      .reduce(_ + _)
  }

  private def mkPool[F[_]](implicit S: Sync[F]): Resource[F, ExecutionContext] = {
    Resource(S.delay {
      val executor = ExecutionContext.fromExecutor((command: Runnable) => command.run())
      val ec = ExecutionContext.fromExecutor(executor)
      (ec, S.delay(()))
    })
  }

  def cleanDir[F[_]: Monad](dir: Path)(implicit sync: Sync[F]): F[Unit] = {
    sync.delay {
      Option(dir.toFile.listFiles)
        .getOrElse(Array.empty[File])
        .toList
    }.flatMap { list =>
      list.traverse { f =>
        if (f.isDirectory) {
          cleanDir[F](f.toPath)
        } else {
          sync.delay {
            f.delete()
          }.as(())
        }
      }.as(())
    }
  }

  def run(args: List[String]): IO[ExitCode] = {
    IO.suspend {
      val apiDir = Paths.get(args.head)
      val dstDir = Paths.get(args.tail.head)
      cleanDir[IO](dstDir).flatMap { _ =>
        generate[IO](apiDir, dstDir)
          .as(ExitCode.Success)
      }
    }
  }
}
