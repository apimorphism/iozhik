package io.github.iozhik

import cats.effect._
import cats.instances.list._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.traverse._
import fs2.RaiseThrowable
import fs2.Stream
import fs2.io.file.Files
import io.github.iozhik.scalagen.ArbitraryGenerator
import io.github.iozhik.scalagen.GeneratorConfig
import org.scalafmt.interfaces.Scalafmt
import pureconfig.ConfigSource

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import scala.annotation.tailrec

object Application extends IOApp {

  def generate[F[_]: RaiseThrowable](cfgPath: Path, dstDir: Path)(implicit async: Async[F], files: Files[F]): F[Unit] = {

    val generator = new ScalaApiGeneratorV1
    val formatter = Scalafmt.create(this.getClass.getClassLoader)
    val cfg = ConfigSource.file(cfgPath).loadOrThrow[GeneratorConfig]

    files.readUtf8(fs2.io.file.Path(cfg.apiPath)).reduce(_ + _)
      .map { code =>
        Parser.Grammar
          .parse(code)
          .flatMap(Generator.Model.translate)
          .flatMap(space => generator.gen(space, cfg))
      }
      .map(_.left.map(new RuntimeException(_)))
      .flatMap(Stream.fromEither(_))
      .flatMap(Stream.emits(_))
      .evalMap(code => async.delay{
        val path = dstDir.resolve(code.path)
        val file = path.resolve(code.name)
        path.toFile.mkdirs()
        // the generated test sources are formatted with the target repo's own config, which checks them
        val body =
          if (code.path.startsWith(ArbitraryGenerator.TestSourcesPath))
            cfg.gensFmtConfigPath.fold(code.body) { c =>
              formatUntilStable(formatter, dstDir.resolve(c).toAbsolutePath.normalize, file.toAbsolutePath.normalize, code.body)
            }
          else formatter.format(Path.of(cfg.fmtConfigPath), file, code.body)
        (file, body)
      })
      .flatMap { case (file, body) =>
        Stream
          .emit(body)
          .through(fs2.text.utf8.encode)
          .through(files.writeAll(fs2.io.file.Path.fromNioPath(file)))
      }
      .handleErrorWith(e => Stream.eval(async.delay(e.printStackTrace())))
      .compile
      .drain
  }

  // One scalafmt pass over the generated layout isn't always stable, and the target repo checks the result.
  @tailrec
  def formatUntilStable(formatter: Scalafmt, config: Path, file: Path, code: String, passes: Int = 5): String = {
    val next = formatter.format(config, file, code)
    if (next == code || passes <= 1) next else formatUntilStable(formatter, config, file, next, passes - 1)
  }

  def cleanDir[F[_]](dir: Path)(implicit sync: Sync[F]): F[Unit] = {
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
      val apiDir = Paths.get(args.head)
      val dstDir = Paths.get(args.tail.head)
      // the generated test sources have a tree of their own, so they are wiped too
      (cleanDir[IO](dstDir) >> cleanDir[IO](dstDir.resolve(ArbitraryGenerator.TestSourcesPath))).flatMap { _ =>
        generate[IO](apiDir, dstDir)
          .as(ExitCode.Success)
      }
  }
}
