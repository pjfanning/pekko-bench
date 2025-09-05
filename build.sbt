ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

enablePlugins(JmhPlugin)

lazy val root = (project in file("."))
  .settings(
    name := "pekko-bench",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor" % "1.2.0",
      "io.netty" % "netty-common" % "4.2.5.Final",
      "io.netty" % "netty-buffer" % "4.2.5.Final",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    ),
    // needed for StringAccess (uses VarHandle on private field in String class)
    Test / javaOptions += "--add-opens=java.base/java.lang=ALL-UNNAMED"
  )
