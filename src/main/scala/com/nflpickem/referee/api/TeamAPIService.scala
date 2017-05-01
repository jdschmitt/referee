package com.nflpickem.referee.api

import com.nflpickem.referee.service.TeamService
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 3/3/17.
  */
trait TeamAPIService extends HttpService {

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def teamsRoute: Route =
    path( "teams" ) {
      get {
        complete {
          TeamService.allTeams
        }
      }
    }

}
