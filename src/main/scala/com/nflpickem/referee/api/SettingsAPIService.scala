package com.nflpickem.referee.api

import com.nflpickem.referee.service.{SeasonService, SettingsService}
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 2/1/17.
  */
trait SettingsAPIService extends HttpService {

  import com.nflpickem.referee.model.SettingsJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  def settingsRoute: Route =
    pathPrefix("api") {
      path("settings") {
        get {
          complete {
            SettingsService.getSettings
          }
        }
      } ~
      path("currentWeek") {
        get {
          complete {
            SeasonService.currentWeek
          }
        }
      }
    }

}
