## Vert.x Scala sbt project compiled with Dotty

### Usage

This is a normal sbt project, you can compile code with `sbt compile` and run it
with `sbt run`, `sbt console` will start a Dotty REPL.

This is an adapted version of the Vert.x Scala g8 template to use with Dotty nightly. 

### Changes needed 

Here's a list of changes needed to adapt the g8 template to Dotty:
* in order to use scalatest, we have to use Dotty 0.24-RC1 at the moment, there's no 0.25 yet
* 0.24 causes an issue with codegen. Looked fixed in 0.25 though (but since the project wasn't compiling because of scalatest... no way to be sure), so the explicit dependency to codegen should not be needed in upcoming Dotty versions
* the test method used to deploy verticles by implicitly resolving `TypeTag` does not seem to be supported in Dotty 0.24 (there's no implicit TypeTag for classes if I understand correctly), an easy fix (relying on `ClassTag` can be found in the `VerticleTesting`), not sure it's what we truly want, though
* a few (given) imports needed here and there (postfixOps, implicit resolutions, etc.)
* plugins (like Docker and Assembly) need a few adaptation that can be found in `project/Build.scala`
  * `Version.scala` has been removed, automatically set to 0.24
  * the console template has been changed (`given ... as`)
  * the `nameForVerticle` method no longer works because of the `TypeTag` broken in dotty (see above). This method could probably be changed in favor of `ClassTag` if it's desirable.
  * `docker` runs totally fine
  * `package` builds a proper fat jar in `target/scala-0.24/dotty-simple-assembly-0.0.1.jar`, no problem
  
  
Overall, the amount of required changes are really minimal. The only annoying thing so far is `TypeTag` which is not bound to `vertx-lang-scala` itself but the way we the user wants to deploy Verticles.
The only required work in `vertx-lang-scala` would be to change the `nameForVerticle` method. Every other change would happen in the g8 template. 
