ThisBuild / version      := "0.0.1"
ThisBuild / organization := "io.github.iozhik"

val settings = Compiler.settings ++ Seq()

lazy val iozhik = project
  .enablePlugins(JavaAppPackaging)
	.settings( settings: _* )
  .settings(libraryDependencies ++= Dependencies.iozhik)

lazy val `iozh-api-scraper` = project
  .dependsOn(iozhik)
  .settings( settings: _* )
  .settings(libraryDependencies ++= Dependencies.iozhApiScraper)

lazy val iozhtest = project
  .dependsOn(iozhik)
  .settings( settings: _* )
  .settings(libraryDependencies ++= Dependencies.iozhtest)

lazy val javatest = project
  .dependsOn(iozhik)
  .settings( settings: _* )
  .settings(libraryDependencies ++= Dependencies.javatest)

lazy val root = (project in file("."))
  .settings(
    name := "IOzhik"
  )
	.aggregate(
    iozhik,
    iozhtest,
    javatest,
    `iozh-api-scraper`,
  )
