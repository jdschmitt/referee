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
  with PlayerAPIService
  with SettingsAPIService
  with TeamAPIService {

  val context:ActorContext

  def webappRoute: Route =
    path("")(getFromResource("webapp/dist/index.html")) ~ getFromResourceDirectory("webapp/dist")

  def route: Route = respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
    playersRoute ~ settingsRoute ~ webappRoute ~ teamsRoute
  }

}
