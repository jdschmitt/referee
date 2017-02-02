package com.nflpickem.referee.api

import com.nflpickem.referee.SettingsService
import spray.routing.HttpService

/**
  * Created by jason on 2/1/17.
  */
trait SettingsAPIService extends HttpService {

  import com.nflpickem.referee.model.SettingsJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  def settingsRoute =
    path( "settings" ) {
      get {
        complete {
          SettingsService.get
        }
      }
    }

}
