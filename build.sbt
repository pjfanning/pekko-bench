ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.17"

enablePlugins(JmhPlugin)

resolvers += Resolver.ApacheMavenSnapshotsRepo

lazy val root = (project in file("."))
  .settings(
    name := "pekko-bench",
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor" % "2.0.0-M0+201-30a00469-SNAPSHOT",
      "org.parboiled" %% "parboiled" % "2.5.1",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    ),
    // needed for StringAccess (uses VarHandle on private field in String class)
    Test / javaOptions += "--add-opens=java.base/java.lang=ALL-UNNAMED"
  )
