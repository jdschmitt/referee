package com.nflpickem.referee.api

import com.nflpickem.referee.Referee
import com.nflpickem.referee.dao.SeasonDatabase
import com.nflpickem.referee.service.SettingsService
import com.nflpickem.referee.util.WeekHelper
import spray.http.StatusCodes
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 2/1/17.
  */
trait SettingsAPIService extends HttpService {

  import com.nflpickem.referee.model.SettingsJsonProtocol._
  import net.codingwell.scalaguice.InjectorExtensions._
  import spray.httpx.SprayJsonSupport._
  val weekHelper: WeekHelper = Referee.injector.instance[WeekHelper]
  val seasonDb: SeasonDatabase = Referee.injector.instance[SeasonDatabase]

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
            seasonDb.currentSeason match {
              case Some(season) => weekHelper.currentWeek(season)
              case None => StatusCodes.NotFound
            }
          }
        }
      }
    }

}
