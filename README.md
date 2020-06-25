## Vert.x Scala sbt project compiled with Dotty

### Usage

This is a normal sbt project, you can compile code with `sbt compile` and run it
with `sbt run`, `sbt console` will start a Dotty REPL.

This is an adapted version of the Vert.x Scala g8 template to use with Dotty nightly. 

### Changes needed 

Here's a list of changes needed to adapt the g8 template to Dotty:
* in order to use scalatest, we have to use Dotty 0.24-RC1 can be used at the moment, there's no 0.25 version is not ready
* 0.24 causes an issue with codegen. Looks fixed in 0.25 though, so the explicit dependency to codegen should not be needed in upcoming Dotty versions
* the method used to deploy verticles by implicitly resolving `TypeTag` does not seem to be supported in Dotty 0.24 (there's no implicit TypeTag for classes apparently), an easy fix (relying on `ClassTag` can be found in the `VerticleTesting`)
* a few (given) imports needed here and there

