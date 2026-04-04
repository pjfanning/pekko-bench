ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.18"

enablePlugins(JmhPlugin)

resolvers += Resolver.ApacheMavenSnapshotsRepo

lazy val root = (project in file("."))
  .settings(
    name := "pekko-bench",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor" % "2.0.0-M1",
      "org.parboiled" %% "parboiled" % "2.5.1",
      "org.scalatest" %% "scalatest" % "3.2.20" % Test
    )
  )
