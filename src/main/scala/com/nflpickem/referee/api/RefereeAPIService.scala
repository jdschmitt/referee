package com.nflpickem.referee.api

import akka.actor.{Actor, ActorContext}
import spray.http.HttpHeaders.RawHeader
import spray.routing._

/**
  * Created by jason on 1/31/17.
  */

class RefereeServiceActor extends HttpServiceActor with RefereeAPIService {

  def receive: Actor.Receive = runRoute(route)

}

trait RefereeAPIService extends HttpService
  with GameAPIService
  with PlayerAPIService
  with SettingsAPIService
  with TeamAPIService {

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

  def route: Route = respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
    playersRoute ~ settingsRoute ~ webappRoute ~ secondPotRoute ~ picksRoute ~ groupPicksRoute ~ gameAdminRoute ~
      teamsRoute ~ gamesRoute
  }

}
