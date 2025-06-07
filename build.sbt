ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

enablePlugins(JmhPlugin)

lazy val root = (project in file("."))
  .settings(
    name := "pekko-bench",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor" % "1.1.3",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    )
  )
