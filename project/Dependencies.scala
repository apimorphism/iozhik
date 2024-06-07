import sbt._

object Dependencies {

  object V {
    val catsCore   = "2.12.0"
    val catsEffect = "3.5.4"
    val catsMtl    = "0.7.1"
    val circe      = "0.14.7"
    val shapeless  = "2.3.3"
    val atto       = "0.9.5"
    val scalaTest  = "3.2.18"
    val fs2Core    = "3.10.2"
    val fs2IO      = "3.10.2"
    val scalaFmt   = "3.0.6"
    val scodecCore = "1.11.10"
    val scodecBits = "1.2.0"
    val http4s     = "0.23.27"
    val slf4j      = "1.7.26"
    val logback    = "1.2.3"
    val scraper    = "3.0.0"
    val uPickle    = "3.3.1"
    val javaFmt    = "1.7"
    val junit      = "4.12"
    val jackson    = "2.9.8"
    val unirest    = "1.4.9"
  }

  val fs2Core    = "co.fs2" %% "fs2-core" % V.fs2Core
  val fs2IO      = "co.fs2" %% "fs2-io" % V.fs2IO
  val catsCore   = "org.typelevel" %% "cats-core" % V.catsCore
  val catsEffect = "org.typelevel" %% "cats-effect" % V.catsEffect
  val catsMtl    = "org.typelevel" %% "cats-mtl-core" % V.catsMtl
  val shapeless  = "com.chuusai" %% "shapeless" % V.shapeless
  val scalaFmt   = "org.scalameta" %% "scalafmt-dynamic" % V.scalaFmt
  val javaFmt    = "com.google.googlejavaformat" % "google-java-format" % V.javaFmt

  val scalaTest  = "org.scalatest" %% "scalatest" % V.scalaTest % Test
  val javaTest   = "junit" % "junit" % V.junit % Test

  val atto  = Seq(
    "org.tpolecat" %% "atto-core"    % V.atto,
    "org.tpolecat" %% "atto-refined" % V.atto,
  )

  val circe = Seq(
    "io.circe" %% "circe-core"           % V.circe,
    "io.circe" %% "circe-parser"         % V.circe,
    "io.circe" %% "circe-generic"        % V.circe,
    "io.circe" %% "circe-generic-extras" % "0.14.3",
  )

  val scodec = Seq(
    "org.scodec" %% "scodec-core" % V.scodecCore,
    "org.scodec" %% "scodec-core" % V.scodecBits,
  )

  val http4s = Seq(
    "org.http4s" %% "http4s-dsl"          % V.http4s,
    "org.http4s" %% "http4s-circe"        % V.http4s,
    "org.http4s" %% "http4s-blaze-server" % "0.23.16",
    "org.http4s" %% "http4s-blaze-client" % "0.23.16",
  )

  val unirest = Seq(
    "com.mashape.unirest" % "unirest-java" % "1.4.9"
  )

  val logger = Seq(
    "org.slf4j"      % "slf4j-api"       % V.slf4j,
    "ch.qos.logback" % "logback-classic" % V.logback,
  )

  val scraper = Seq(
    "net.ruippeixotog" %% "scala-scraper" % V.scraper
  )

  val uPickle = Seq(
    "com.lihaoyi" %% "upickle" % V.uPickle,
    "com.lihaoyi" %% "upack" % V.uPickle,
  )

  val jackson = Seq(
    "com.fasterxml.jackson.core" % "jackson-core" % V.jackson,
    "com.fasterxml.jackson.core" % "jackson-databind" % V.jackson,
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % V.jackson
  )

  val common = circe ++ jackson ++ Seq(
    fs2Core,
    fs2IO,
    catsCore,
    catsEffect,
    catsMtl,
    shapeless,
    scalaFmt,
    scalaTest,
    javaTest,
    javaFmt
  )

  val iozhik: Seq[ModuleID]   = common ++ atto
  val iozhtest: Seq[ModuleID] = common ++ circe ++ scodec ++ http4s ++ uPickle ++ logger
  val javatest: Seq[ModuleID] = common ++ jackson ++ unirest
  val iozhApiScraper: Seq[ModuleID] = common ++ scraper
}
