package com.nflpickem.referee.api

import com.nflpickem.referee.model.Season
import com.nflpickem.referee.service.SeasonService
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 2/1/17.
  */
trait SeasonAPIService extends HttpService {

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def seasonsRoute: Route =
    path( "seasons" ) {
      get {
        complete {
          SeasonService.getSeasons
        }
      } ~
      post {
        entity(as[Season]) { season =>
          complete {
            SeasonService.insertSeason(season)
          }
        }
      }
    }

}
