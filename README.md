## Vert.x Scala sbt project compiled with Dotty

### Usage

This is a normal sbt project, you can compile code with `sbt compile` and run it
with `sbt run`, `sbt console` will start a Dotty REPL.

This is an adapted version of the Vert.x Scala g8 template to use with Dotty nightly. 

### Changes needed 

Here's a list of changes needed to adapt the g8 template to Dotty:
* plugins (like Docker and Assembly) have been removed, because of: https://github.com/lampepfl/dotty-example-project/issues/36 => need to follow improvements
* in order to use scalatest, we have to use Dotty 0.24-RC1 at the moment, there's no 0.25 yet
* 0.24 causes an issue with codegen. Looked fixed in 0.25 though (but since the project wasn't compiling because of scalatest... no way to be sure), so the explicit dependency to codegen should not be needed in upcoming Dotty versions
* the test method used to deploy verticles by implicitly resolving `TypeTag` does not seem to be supported in Dotty 0.24 (there's no implicit TypeTag for classes if I understand correctly), an easy fix (relying on `ClassTag` can be found in the `VerticleTesting`), not sure it's what we truly want, though
* a few (given) imports needed here and there (postfixOps, implicit resolutions, etc.)

