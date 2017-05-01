package com.nflpickem.referee.api

import com.nflpickem.referee.model.Game
import com.nflpickem.referee.service.GameService
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 3/3/17.
  */
trait GameAPIService extends HttpService {

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def gamesRoute: Route =
    path( "games" ) {
      pathEndOrSingleSlash {
        get {
          parameters('week) { week =>
            complete {
              GameService.getGamesForWeek(week.toInt)
            }
          }
        } ~
        post {
          entity(as[Game]) { game =>
            complete {
              GameService.insertGame(game)
            }
          }
        }
      }
    }

}
