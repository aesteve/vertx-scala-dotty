package com.github.aesteve

import io.vertx.scala.core._
import org.scalatest.matchers.should.Matchers
import org.scalatest.{given _}
import scala.language.implicitConversions

class BusVerticleSpec extends VerticleTesting[BusVerticle] with Matchers:

  "BusVerticle" should "reply to a message" in {
    vertx.eventBus
      .requestFuture[String] ("testAddress", Some ("msg") )
      .map (res => res.body () should equal ("Hello World!") )
  }
