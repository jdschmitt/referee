package com.nflpickem.referee

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.google.inject.Guice
import com.nflpickem.referee.api.RefereeServiceActor
import com.nflpickem.referee.db.RefereeMySqlConnectionPool
import org.flywaydb.core.Flyway
import spray.can.Http

import scala.io.StdIn
import scala.util.Properties


/**
 * Main class for the service actor and can be stopped by hitting the `"e"` key.
 */
object Boot extends App {

  private def waitForExit() = {
    def waitEOF(): Unit = StdIn.readLine() match {
      case "exit" => Referee.system.shutdown()
      case _ => waitEOF()
    }
    waitEOF()
  }

  Referee.initialize()

  Referee.system.log.info("Booting Referee server...")

  Referee.startHttpServer()

  waitForExit()
  Referee.system.shutdown()

}

object Referee {

  val config = RefereeConfig

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("referee")
  val injector = Guice.createInjector(new RefereeModule)

  def startDbConnectionPool(): Unit = {
    RefereeMySqlConnectionPool.startup(MySqlConfig)
    system.registerOnTermination {
      system.log.info("Shutting down MySQL connection pool...")
      RefereeMySqlConnectionPool.shutdown()
    }
  }

  def startHttpServer(): Unit = {
    // create and start our service actor
    val service = system.actorOf(Props[RefereeServiceActor], "service-actor")

    // start a new HTTP server on port 8080 with our service actor as the handler
    val interface = ServiceSettings(system).interface
    val port = Properties.envOrElse("PORT", ServiceSettings(system).port).toInt
    IO(Http) ! Http.Bind(service, interface, port)

    Console.println(s"Server started ${system.name}, $interface:$port")
    Console.println("Type `exit` to exit....")
  }

  def initialize(): Unit = {
    migrateDb()
    startDbConnectionPool()
  }

  def migrateDb(): Unit = {
    val flyway = new Flyway()
    flyway.setDataSource(MySqlConfig.jdbcUrl, MySqlConfig.user, MySqlConfig.password)

    flyway.setBaselineOnMigrate(false)
    flyway.migrate()
  }

}