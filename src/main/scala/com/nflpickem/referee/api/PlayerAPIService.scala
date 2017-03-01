package com.nflpickem.referee.api

import com.nflpickem.referee.service.PlayerService
import spray.routing.HttpService

/**
  * Created by jason on 2/1/17.
  */
trait PlayerAPIService extends HttpService {

  import com.nflpickem.referee.model.PlayerJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  def playersRoute =
    path( "players" ) {
      get {
        complete {
          PlayerService.allPlayers
        }
      }
    } ~ path("mainPotRanking") {
      get {
        complete {
          PlayerService.mainPotRanking()
        }
      }
    } ~ path("secondPotRanking") {
      get {
        complete {
          PlayerService.secondPotRanking()
        }
      }
    }

}
