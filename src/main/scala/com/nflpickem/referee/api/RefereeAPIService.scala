package com.nflpickem.referee.api

import akka.actor.{Actor, ActorContext}
import com.nflpickem.referee.CORSSupport
import com.nflpickem.referee.dao.{SeasonDAO, SeasonDatabase}
import spray.http.StatusCodes.{BadRequest, InternalServerError}
import spray.routing._

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by jason on 1/31/17.
  */

class RefereeServiceActor extends HttpServiceActor with RefereeAPIService {

  implicit def exceptionHandler = ExceptionHandler {
    case ex: IllegalArgumentException => complete(BadRequest -> ex.getMessage)
    case e: Throwable => complete(InternalServerError, e.getMessage)
  }

  def receive: Actor.Receive = runRoute(route)

}

trait RefereeAPIService extends HttpService
  with AuthAPIService
  with CORSSupport
  with GameAPIService
  with PickAPIService
  with PlayerAPIService
  with SeasonAPIService
  with SettingsAPIService
  with TeamAPIService {

  // TODO Required because more than 1 mixin declares this variable and this prevents the collision. Makes me think that
  // there is a better way to get at dependencies from traits
  override val seasonDb: SeasonDatabase = SeasonDAO

  override implicit def executionContext: ExecutionContextExecutor = actorRefFactory.dispatcher

  val context:ActorContext

  def webappRoute: Route =
    path("")(getFromResource("webapp/index.html")) ~ getFromResourceDirectory("webapp")

  def secondPotRoute: Route =
    path("secondPot")(getFromResource("webapp/index.html")) ~ getFromResourceDirectory("webapp")

  def picksRoute: Route =
    path("picks")(getFromResource("webapp/index.html")) ~ getFromResourceDirectory("webapp")

  def groupPicksRoute: Route =
    path("groupPicks")(getFromResource("webapp/index.html")) ~ getFromResourceDirectory("webapp")

  def gameAdminRoute: Route =
    path("gameAdmin")(getFromResource("webapp/index.html")) ~ getFromResourceDirectory("webapp")

  def settingsPageRoute: Route =
    path("settings")(getFromResource("webapp/index.html")) ~ getFromResourceDirectory("webapp")

  def weeklyWinnersPageRoute: Route =
    path("weeklyWinners")(getFromResource("webapp/index.html")) ~ getFromResourceDirectory("webapp")

  def route: Route = cors {
    authRoute ~
      gameAdminRoute ~
      gamesRoute ~
      groupPicksRoute ~
      picksRoute ~
      picksApiRoute ~
      playersRoute ~
      seasonsRoute ~
      secondPotRoute ~
      settingsPageRoute ~
      settingsRoute ~
      teamsRoute ~
      webappRoute ~
      weeklyWinnersPageRoute
  }

}
