organization  := "com.sprangular"

version       := "0.1"

scalaVersion  := "2.11.8"

name          := "samsreferee"

enablePlugins(JavaAppPackaging)

// spray-resolver setting
seq(Revolver.settings: _*)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

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

// watch webapp files
watchSources <++= baseDirectory map { path => ((path / "client") ** "*").get }

libraryDependencies ++= {
  val AKKA_VERSION = "2.3.12"
  val SPRAY_VERSION = "1.3.3"
  Seq(
    "io.spray"            %% "spray-can"            % SPRAY_VERSION,
    "io.spray"            %% "spray-client"         % SPRAY_VERSION,
    "io.spray"            %% "spray-routing"        % SPRAY_VERSION,
    "io.spray"            %% "spray-json"           % SPRAY_VERSION,
    "org.scalaj"          %% "scalaj-http"          % "2.3.0",
    "org.json4s"          %% "json4s-native"        % "3.5.2",
    "org.scalikejdbc"     %% "scalikejdbc"          % "2.5.0",
    "mysql"               %  "mysql-connector-java" % "5.1.39",
    "com.zaxxer"          %  "HikariCP"             % "2.4.7",
    "joda-time"           %  "joda-time"            % "2.9.7",
    "org.flywaydb"        %  "flyway-core"          % "4.1.1",
    "io.spray"            %% "spray-testkit"        % SPRAY_VERSION % "test",
    "com.typesafe.akka"   %% "akka-actor"           % AKKA_VERSION,
    "com.typesafe.akka"   %% "akka-testkit"         % AKKA_VERSION  % "test",
    "com.typesafe.akka"   %% "akka-slf4j"           % AKKA_VERSION,
    "org.scalatest"       %% "scalatest"            % "3.0.1"       % "test",
    "io.github.nremond"   %% "pbkdf2-scala"         % "0.5"
  )
}

flywayUrl := "jdbc:mysql://localhost:3306/pickem"

flywayUser := "root"

flywayPassword := ""

lazy val buildFrontEnd: TaskKey[Unit] = taskKey[Unit]("Execute the npm build command to build the ui")

buildFrontEnd := {
  val s: TaskStreams = streams.value
  s.log.info("Building front-end")
  val shell: Seq[String] = Seq("bash", "-c")
  val npmInstall: Seq[String] = shell :+ "npm install"
  val npmBuild: Seq[String] = shell :+ "npm run build"

  if ((npmInstall #&& npmBuild !) == 0) {
    s.log.success("Successfully built front-end.")
  } else {
    throw new IllegalStateException("Failed to build front-end.")
  }
}

// Executes when running within IDE
//(run in Compile) <<= (run in Compile).dependsOn(buildFrontEnd)
// Executes when running "sbt compile"
compile <<= (compile in Compile) dependsOn buildFrontEnd
