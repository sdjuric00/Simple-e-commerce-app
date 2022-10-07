name := "back"

version := "1.0"

lazy val `back` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.13.5"


libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice,
                      "com.typesafe.play" %% "play-slick" % "5.0.0",
                      "org.postgresql" % "postgresql" % "42.5.0",
                      "com.typesafe.play" % "play-iteratees_2.10" % "2.4.11",
                      "com.typesafe.slick" %% "slick-codegen" % "3.3.3",
                      "org.playframework.anorm" %% "anorm" % "2.7.0",
                      "com.github.jwt-scala" %% "jwt-play-json" % "9.1.1",
                      "com.github.jwt-scala" %% "jwt-play-json" % "9.1.1",
                      "com.typesafe.play" %% "play-mailer" % "8.0.1",
                      "com.typesafe.play" %% "play-mailer-guice" % "8.0.1")
