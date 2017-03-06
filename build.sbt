version := "1.0.0"

scalaVersion := "2.11.8"

name := "referee"

enablePlugins(JavaAppPackaging)

// spray-resolver setting
seq(Revolver.settings: _*)

// watch webapp files
watchSources <++= baseDirectory map { path => ((path / "src" / "main" / "webapp") ** "*").get }

resolvers += "spray repo" at "http://repo.spray.io"

val AKKA_VERSION = "2.3.12"
val SPRAY_VERSION = "1.3.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AKKA_VERSION,
  "com.typesafe.akka" %% "akka-remote" % AKKA_VERSION,
  "com.typesafe.akka" %% "akka-testkit" % AKKA_VERSION,
  "com.typesafe.akka" %% "akka-slf4j" % AKKA_VERSION,
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "io.spray" %% "spray-client" % SPRAY_VERSION,
  "io.spray" %% "spray-can" % SPRAY_VERSION,
  "io.spray" %% "spray-routing" % SPRAY_VERSION,
  "io.spray" %% "spray-json" % SPRAY_VERSION,
  "org.scalikejdbc" %% "scalikejdbc" % "2.5.0",
  "mysql" % "mysql-connector-java" % "5.1.39",
  "com.zaxxer" % "HikariCP" % "2.4.7",
  "joda-time" % "joda-time" % "2.9.7",
  "org.flywaydb" % "flyway-core" % "4.1.1"
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

val additionalClasses = file("webapp/client")

unmanagedClasspath in Compile += additionalClasses

flywayUrl := "jdbc:mysql://localhost:3306/pickem"

flywayUser := "root"

flywayPassword := ""

lazy val buildFrontEnd = taskKey[Unit]("Execute the npm build command to build the ui")

buildFrontEnd := {
  val s: TaskStreams = streams.value
  s.log.info("Building front-end")
  val shell: Seq[String] = Seq("bash", "-c")
  // TODO Surely there is a way to clean this up better but this works!
  val npmInstall: Seq[String] = shell :+ "cd src/main/webapp && npm install && npm run build"

  if ((npmInstall !) == 0) {
    s.log.success("Successfully built front-end.")
  } else {
    throw new IllegalStateException("Failed to build front-end.")
  }
}

// Executes when running within IDE
(run in Compile) <<= (run in Compile).dependsOn(buildFrontEnd)
// Executes when running "sbt compile"
compile <<= (compile in Compile) dependsOn buildFrontEnd