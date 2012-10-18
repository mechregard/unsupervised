import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "aserv"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
          "com.typesafe.akka" % "akka-kernel" % "2.0.3",
          "com.typesafe.akka" % "akka-remote" % "2.0.3",
       	  "com.typesafe.akka" % "akka" % "2.0"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    )

    val container = Project("service", file("container")).settings(
      resolvers += "akka-resolver-0" at "http://repo.typesafe.com/typesafe/releases"
    ).dependsOn(main)
}
