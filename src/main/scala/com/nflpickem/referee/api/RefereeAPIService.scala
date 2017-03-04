package com.nflpickem.referee.api

import akka.actor.ActorContext
import spray.http.HttpHeaders.RawHeader
import spray.routing._

/**
  * Created by jason on 1/31/17.
  */

class RefereeServiceActor extends HttpServiceActor with RefereeAPIService {

  def receive = runRoute(route)

}

trait RefereeAPIService extends HttpService
  with PlayerAPIService
  with SettingsAPIService {

  val context:ActorContext

  def webappRoute: Route =
    path("")(getFromResource("webapp/src/index.html")) ~ getFromResourceDirectory("webapp/src")

  def route = respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
    playersRoute ~ settingsRoute ~ webappRoute
  }

}
