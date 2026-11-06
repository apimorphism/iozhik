package io.github.iozhik

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import io.github.iozhik.scalagen.{GeneratorConfig, OpenEnumConfig}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Files

class ApplicationSpec extends AnyFlatSpec with Matchers {

  "The test output directory" must "be left untouched when generate-gens is off" in {
    val api = Files.createTempFile("iozhik", ".api")
    Files.writeString(
      api,
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    Dated {
        |      date: Long
        |    }
        |  }
        |}
      """.stripMargin
    )
    val dstDir  = Files.createTempDirectory("iozhik-main")
    val testDir = Files.createTempDirectory("iozhik-test")
    val kept    = Files.writeString(testDir.resolve("Kept.scala"), "object Kept")
    val cfg = GeneratorConfig(
      apiPath = api.toString,
      fmtConfigPath = "iozhtest/src/main/resources/.scalafmt.conf",
      openEnum = OpenEnumConfig(enabled = false, excludeTypes = List.empty)
    )

    Application.generate[IO](cfg, dstDir, Some(testDir)).unsafeRunSync()

    Files.exists(dstDir.resolve("telegramium/bots/Dated.scala")) shouldBe true
    Files.readString(kept) shouldBe "object Kept"
  }
}
