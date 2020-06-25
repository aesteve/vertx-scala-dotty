package com.github.aesteve

import io.vertx.lang.scala.json.{Json, JsonObject}
import io.vertx.lang.scala.{ScalaVerticle, VertxExecutionContext}
import io.vertx.scala.core._
import org.scalatest.BeforeAndAfter
import org.scalatest.{given _}
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._
import scala.language.{implicitConversions, postfixOps}
import scala.reflect.ClassTag
import scala.util.{Failure, Success}

abstract class VerticleTesting[A <: ScalaVerticle: ClassTag] extends AsyncFlatSpec with BeforeAndAfter:
  val vertx = Vertx.vertx()
  given vertxExecutionContext as ExecutionContext = 
    VertxExecutionContext(vertx.getOrCreateContext)

  private var deploymentId = ""

  val config: JsonObject = Json.emptyObj()

  before {
    deploymentId = Await.result(
      vertx
        .deployVerticleFuture("scala:" + summon[ClassTag[A]].runtimeClass.getName,
          DeploymentOptions().setConfig(config))
        .andThen {
          case Success(d) => d
          case Failure(t) => throw new RuntimeException(t)
        },
      10000 millis
    )
  }

  after {
    Await.result(
      vertx.undeployFuture(deploymentId)
        .andThen {
          case Success(d) => d
          case Failure(t) => throw new RuntimeException(t)
        },
      10000 millis
    )
  }
