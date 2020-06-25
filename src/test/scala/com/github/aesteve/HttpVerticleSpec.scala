package com.github.aesteve

import io.vertx.scala.ext.web.client._
import org.scalatest.matchers.should.Matchers
import org.scalatest.{given _}
import scala.language.implicitConversions


class HttpVerticleSpec extends VerticleTesting[HttpVerticle] with Matchers:

  "HttpVerticle" should "bind to 8666 and answer with 'world'" in {
    WebClient.create(vertx, WebClientOptions().setDefaultHost("127.0.0.1").setDefaultPort(8666))
      .get("/hello")
      .sendFuture()
      .map(_.bodyAsString should equal("world"))
  }
