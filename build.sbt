lazy val root = (project in file(".")).
    settings(
        name := "scala-rolling-ball-clock",
        version := "1.0",
        scalaVersion := "2.11.6"
    )
    libraryDependencies += "org.scalatest" % "scalatest" % "2.2.4" % "test"