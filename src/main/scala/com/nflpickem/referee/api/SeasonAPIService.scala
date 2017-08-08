package com.nflpickem.referee.api

import com.nflpickem.referee.{Referee, Whistle}
import com.nflpickem.referee.dao.SeasonDatabase
import com.nflpickem.referee.model.Season
import spray.http.StatusCodes
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 2/1/17.
  */
trait SeasonAPIService extends HttpService with Whistle {

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._
  import net.codingwell.scalaguice.InjectorExtensions._
  val seasonDb: SeasonDatabase = Referee.injector.instance[SeasonDatabase]

  def seasonsRoute: Route =
    pathPrefix("api") {
      pathPrefix("seasons") {
        pathEnd {
          post {
            entity(as[Season]) { season =>
              complete {
                seasonDb.insertSeason(season)
              }
            }
          } ~
          get {
            complete(seasonDb.getSeasons)
          }
        } ~
        path(LongNumber) { id =>
          delete {
            complete {
              if (seasonDb.deleteSeason(id))
                StatusCodes.NoContent
              else {
                log.error(s"Unable to delete season with ID $id")
                StatusCodes.InternalServerError
              }
            }
          } ~
          get {
            complete(seasonDb.getSeason(id))
          }
        }
      }
    }
}
