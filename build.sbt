import Docker.autoImport.exposedPorts
import sbt.Package.ManifestAttributes

val dottyVersion = "0.24.0-RC1"

enablePlugins(DockerPlugin)
exposedPorts := Seq(8666)

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-simple",
    version := "0.0.1",
    scalaVersion := dottyVersion,
    resolvers ++= Vector(
      "Sonatype SNAPSHOTS" at "https://oss.sonatype.org/content/repositories/snapshots/"
    ),
    libraryDependencies ++= Vector (
      (Library.vertx_lang_scala),
      Library.vertx_web,
      Library.vertx_codegen, // else: A signature refers to Fluent/T in package io.vertx.codegen.annotations which is not available
      Library.vertx_web_client  % "test",
    ).map(_.withDottyCompat(scalaVersion.value)),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.0" % "test"
    ),
    packageOptions += ManifestAttributes(
      ("Main-Verticle", "scala:com.github.aesteve.HttpVerticle"))

  )
