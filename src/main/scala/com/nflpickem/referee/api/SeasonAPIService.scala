package com.nflpickem.referee.api

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Season
import com.nflpickem.referee.service.SeasonService
import spray.http.StatusCodes
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 2/1/17.
  */
trait SeasonAPIService extends HttpService with Whistle {

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def seasonsRoute: Route =
    pathPrefix("seasons") {
      pathEnd {
        post {
          entity(as[Season]) { season =>
            complete {
              SeasonService.insertSeason(season)
            }
          }
        } ~
        get {
          complete(SeasonService.getSeasons)
        }
      } ~
        path(LongNumber) { id =>
          delete {
            complete {
              if (SeasonService.deleteSeason(id))
                StatusCodes.NoContent
              else {
                log.error(s"Unable to delete season with ID $id")
                StatusCodes.InternalServerError
              }
            }
          } ~
          get {
            complete(SeasonService.getSeason(id))
          }
        }
    }

}
