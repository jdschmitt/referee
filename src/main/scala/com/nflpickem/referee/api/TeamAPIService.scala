package com.nflpickem.referee.api

import com.nflpickem.referee.Referee
import com.nflpickem.referee.dao.TeamDatabase
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 3/3/17.
  */
trait TeamAPIService extends HttpService {

  import com.nflpickem.referee.model.ApiFormats._
  import net.codingwell.scalaguice.InjectorExtensions._
  import spray.httpx.SprayJsonSupport._
  val teamDB: TeamDatabase = Referee.injector.instance[TeamDatabase]

  def teamsRoute: Route =
    pathPrefix("api") {
      path("teams") {
        get {
          complete {
            teamDB.allTeams
          }
        }
      }
    }

}
