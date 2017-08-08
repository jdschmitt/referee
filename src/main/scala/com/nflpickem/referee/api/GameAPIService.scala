package com.nflpickem.referee.api

import com.nflpickem.referee.dao.GameDatabase
import com.nflpickem.referee.model.Game
import com.nflpickem.referee.validator.RangeValidator
import com.nflpickem.referee.{Referee, Whistle}
import spray.http.StatusCodes
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 3/3/17.
  */
trait GameAPIService extends HttpService with Whistle {

  // TODO Is this the right way to get dependencies here?
  import net.codingwell.scalaguice.InjectorExtensions._
  val gameDatabase: GameDatabase = Referee.injector.instance[GameDatabase]

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def gamesRoute: Route =
    pathPrefix("api") {
      pathPrefix("games") {
        pathEndOrSingleSlash {
          get {
            parameters('week.as[Int]) { week: Int =>
              complete {
                RangeValidator(1, 19).validate("week", week)
                gameDatabase.getGamesForWeek(week)
              }
            }
          } ~
          post {
            entity(as[Game]) { game =>
              complete {
                gameDatabase.insertGame(game)
              }
            }
          }
        } ~
        path(LongNumber) { id =>
          delete {
            complete {
              if (gameDatabase.deleteGame(id))
                StatusCodes.NoContent
              else
                throw new Exception(s"Failed to delete game $id")
            }
          } ~
          get {
            complete(gameDatabase.getGame(id))
          }
        } ~
        path("season" / LongNumber) { id =>
          get {
            complete {
              gameDatabase.getGamesForSeason(id)
            }
          }
        }
      }
    }

}
