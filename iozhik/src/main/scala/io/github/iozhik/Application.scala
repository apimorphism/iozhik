package io.github.iozhik

import cats.effect._
import cats.instances.list._
import cats.syntax.flatMap._
import cats.syntax.foldable._
import cats.syntax.functor._
import cats.syntax.traverse._
import fs2.RaiseThrowable
import fs2.Stream
import fs2.io.file.Files
import io.github.iozhik.scalagen.GeneratorConfig
import org.scalafmt.interfaces.Scalafmt
import pureconfig.ConfigSource

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import scala.annotation.tailrec

object Application extends IOApp {

  def generate[F[_]: RaiseThrowable](cfg: GeneratorConfig, dstDir: Path, testDir: Option[Path])(implicit async: Async[F], files: Files[F]): F[Unit] = {

    val generator = new ScalaApiGeneratorV1
    val formatter = Scalafmt.create(this.getClass.getClassLoader)

    val missingTestDir =
      async.raiseWhen(cfg.generateGens && testDir.isEmpty)(
        new IllegalArgumentException("generate-gens needs a test output directory, the third argument")
      )
    // with generate-gens off nothing would wipe it, so the generators in it would go stale
    val unusedTestDir =
      async.raiseWhen(!cfg.generateGens && testDir.isDefined)(
        new IllegalArgumentException("a test output directory, the third argument, needs generate-gens on")
      )

    missingTestDir >> unusedTestDir >> files.readUtf8(fs2.io.file.Path(cfg.apiPath)).reduce(_ + _)
      .map { code =>
        Parser.Grammar
          .parse(code)
          .flatMap(Generator.Model.translate)
          .flatMap(space => generator.gen(space, cfg))
      }
      .map(_.left.map(new RuntimeException(_)))
      .flatMap(Stream.fromEither(_))
      // every file is generated and formatted before anything is wiped, so a failed regen leaves the old sources in place
      .evalMap(codes => async.delay(codes.map { code =>
        val file = (if (code.test) testDir.get else dstDir).resolve(code.path).resolve(code.name)
        val body =
          if (code.test)
            formatUntilStable(formatter, Path.of(cfg.fmtConfigPath), file, code.body)
          else formatter.format(Path.of(cfg.fmtConfigPath), file, code.body)
        (file, body)
      }))
      // the generated test sources have a tree of their own, so they are wiped too
      .evalTap(_ => cleanDir[F](dstDir) >> testDir.traverse_(cleanDir[F](_)))
      .flatMap(Stream.emits(_))
      .flatMap { case (file, body) =>
        Stream.exec(async.delay(file.getParent.toFile.mkdirs()).void) ++
          Stream
            .emit(body)
            .through(fs2.text.utf8.encode)
            .through(files.writeAll(fs2.io.file.Path.fromNioPath(file)))
      }
      .compile
      .drain
  }

  // One scalafmt pass over the generated layout isn't always stable.
  @tailrec
  def formatUntilStable(formatter: Scalafmt, config: Path, file: Path, code: String, passes: Int = 5): String = {
    val next = formatter.format(config, file, code)
    if (next == code) next
    else if (passes <= 1) throw new RuntimeException(s"scalafmt output for $file didn't settle")
    else formatUntilStable(formatter, config, file, next, passes - 1)
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
      val cfgPath = Paths.get(args.head)
      val dstDir = Paths.get(args.tail.head)
      val testDir = args.lift(2).map(Paths.get(_))
      for {
        cfg <- IO(ConfigSource.file(cfgPath).loadOrThrow[GeneratorConfig])
        _ <- generate[IO](cfg, dstDir, testDir)
      } yield ExitCode.Success
  }
}
