package com.nflpickem.referee.api

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Game
import com.nflpickem.referee.service.{GameService, SeasonService}
import spray.http.StatusCodes
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 3/3/17.
  */
trait GameAPIService extends HttpService with Whistle {

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def gamesRoute: Route =
    pathPrefix("api") {
      pathPrefix("games") {
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
        } ~
        path(LongNumber) { id =>
          delete {
            complete {
              if (GameService.deleteGame(id))
                StatusCodes.NoContent
              else {
                log.error(s"Failed to delete game $id")
                StatusCodes.InternalServerError
              }
            }
          } ~
          get {
            complete(GameService.getGame(id))
          }
        } ~
        path("season" / LongNumber) { id =>
          get {
            complete {
              val seasonId: Long = SeasonService.currentSeason.get.id.get
              GameService.getGamesForSeason(id)
            }
          }
        }
      }
    }

}