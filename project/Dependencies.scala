import sbt._

object Dependencies {

  object V {
    val monix      = "3.0.0-RC2"
    val monixNio   = "0.0.3"
    val catsCore   = "1.4.0"
    val catsEffect = "1.2.0"
    val catsMtl    = "0.4.0"
    val circe      = "0.10.0"
    val shapeless  = "2.3.3"
    val atto       = "0.6.4"
    val scalaTest  = "3.0.5"
    val fs2Core    = "1.0.0"
    val fs2IO      = "1.0.0"
    val scalaFmt   = "3.0.0-RC5"
    val scodecCore = "1.10.3"
    val scodecBits = "1.1.6"
    val http4s     = "0.20.0"
    val slf4j      = "1.7.26"
    val logback    = "1.2.3"
    val scraper    = "2.1.0"
    val msgPack    = "0.8.13"
    val uPickle    = "0.7.1"
    val javaFmt    = "1.7"
    val junit      = "4.12"
    val jackson    = "2.9.8"
    val unirest    = "1.4.9"
  }

  val monix      = "io.monix" %% "monix" % V.monix
  val monixNio   = "io.monix" %% "monix-nio" % V.monixNio
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
    "io.circe" %% "circe-generic-extras" % V.circe,
  )

  val scodec = Seq(
    "org.scodec" %% "scodec-core" % V.scodecCore,
    "org.scodec" %% "scodec-core" % V.scodecBits,
  )

  val http4s = Seq(
    "org.http4s" %% "http4s-dsl"          % V.http4s,
    "org.http4s" %% "http4s-circe"        % V.http4s,
    "org.http4s" %% "http4s-blaze-server" % V.http4s,
    "org.http4s" %% "http4s-blaze-client" % V.http4s,
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

  val msgPack = Seq(
    "org.msgpack" %% "msgpack-scala" % V.msgPack
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
    monix,
    monixNio,
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
  val iozhtest: Seq[ModuleID] = common ++ circe ++ scodec ++ http4s ++ msgPack ++ uPickle ++ logger
  val javatest: Seq[ModuleID] = common ++ jackson ++ unirest
  val iozhApiScraper: Seq[ModuleID] = common ++ scraper
}
