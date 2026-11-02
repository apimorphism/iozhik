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
import io.github.iozhik.scalagen.ArbitraryGenerator
import io.github.iozhik.scalagen.GeneratorConfig
import org.scalafmt.interfaces.Scalafmt
import org.scalafmt.interfaces.ScalafmtReporter
import pureconfig.ConfigSource

import java.io.File
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.nio.file.Path
import java.nio.file.Paths
import scala.annotation.tailrec

object Application extends IOApp {

  def generate[F[_]: RaiseThrowable](cfg: GeneratorConfig, dstDir: Path, testDir: Option[Path])(implicit async: Async[F], files: Files[F]): F[Unit] = {

    val generator = new ScalaApiGeneratorV1
    val formatter = Scalafmt.create(this.getClass.getClassLoader).withReporter(FailingReporter)

    val missingTestDir =
      async.raiseWhen(cfg.generateGens && testDir.isEmpty)(
        new IllegalArgumentException("generate-gens needs a test output directory, the third argument")
      )
    // with generate-gens off nothing would wipe it, so the generators in it would go stale
    val unusedTestDir =
      async.raiseWhen(!cfg.generateGens && testDir.isDefined)(
        new IllegalArgumentException("a test output directory, the third argument, needs generate-gens on")
      )
    // it is wiped, so it must hold only what the regen wrote: not a test tree with hand-written specs in it
    val foreignTestFiles = testDir.traverse_ { dir =>
      async.delay(notGenerated(dir)).flatMap { foreign =>
        async.raiseWhen(foreign.nonEmpty)(
          new IllegalArgumentException(
            s"$dir holds files the regen didn't write, so it won't wipe it: ${foreign.take(5).mkString(", ")}"
          )
        )
      }
    }

    missingTestDir >> unusedTestDir >> foreignTestFiles >> files.readUtf8(fs2.io.file.Path(cfg.apiPath)).reduce(_ + _)
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

  // scalafmt-dynamic reports a failure and hands the code back as it was, which would be written unformatted
  object FailingReporter extends ScalafmtReporter {
    def error(file: Path, message: String): Unit = throw new RuntimeException(s"scalafmt failed on $file: $message")
    def error(file: Path, e: Throwable): Unit = throw new RuntimeException(s"scalafmt failed on $file", e)
    override def error(file: Path, message: String, e: Throwable): Unit =
      throw new RuntimeException(s"scalafmt failed on $file: $message", e)
    def excluded(file: Path): Unit = throw new RuntimeException(s"the scalafmt config excludes $file")
    def parsedConfig(config: Path, scalafmtVersion: String): Unit = println(s"parsed config (v$scalafmtVersion): $config")
    def downloadWriter(): PrintWriter = new PrintWriter(System.err, true)
    def downloadOutputStreamWriter(): OutputStreamWriter = new OutputStreamWriter(System.err)
  }

  // One scalafmt pass over the generated layout isn't always stable.
  @tailrec
  def formatUntilStable(formatter: Scalafmt, config: Path, file: Path, code: String, passes: Int = 5): String = {
    val next = formatter.format(config, file, code)
    if (next == code) next
    else if (passes <= 1) throw new RuntimeException(s"scalafmt output for $file didn't settle")
    else formatUntilStable(formatter, config, file, next, passes - 1)
  }

  // Hidden files (.gitkeep, .DS_Store) are left to the wipe. A symlink counts as foreign: the wipe would follow it.
  def notGenerated(dir: Path): List[Path] =
    Option(dir.toFile.listFiles).getOrElse(Array.empty[File]).toList.flatMap { f =>
      val path = f.toPath
      if (java.nio.file.Files.isSymbolicLink(path)) List(path)
      else if (f.isDirectory) notGenerated(path)
      else if (f.getName.startsWith(".")) Nil
      else if (hasMarker(path)) Nil
      else List(path)
    }

  // the marker sits right under the package and imports
  private def hasMarker(file: Path): Boolean =
    scala.util.Using.resource(java.nio.file.Files.newBufferedReader(file, java.nio.charset.StandardCharsets.UTF_8))(
      _.lines().limit(20).anyMatch(_.contains(ArbitraryGenerator.GeneratedMarker))
    )

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
