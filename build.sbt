version := "1.0.0"

scalaVersion := "2.11.8"

name := "referee"

enablePlugins(JavaAppPackaging)

// webapp task
resourceGenerators in Compile <+= (resourceManaged, baseDirectory) map { (managedBase, base) =>
  val webappBase = base / "src" / "main" / "webapp"
  for {
    (from, to) <- webappBase ** "*" x rebase(webappBase, managedBase / "main" / "webapp")
  } yield {
    Sync.copy(from, to)
    to
  }
}

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
  "com.zaxxer" % "HikariCP" % "2.4.7"
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