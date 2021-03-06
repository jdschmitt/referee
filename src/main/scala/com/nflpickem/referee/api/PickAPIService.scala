package com.nflpickem.referee.api

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Player
import com.nflpickem.referee.service.PickService
import com.nflpickem.referee.validator.RangeValidator
import spray.routing._

/**
  * Created by jason on 7/2/17.
  */
trait PickAPIService extends TokenAuth with Whistle {

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def picksApiRoute: Route =
    pathPrefix("api") {
      path("picks") {
        tokenAuth { player: Player =>
          get {
            parameters('week.as[Int], 'season.?.as[Long]) { (week: Int, season: Long) =>
              RangeValidator(1, 19).validate("week", week)
              complete(PickService.picksForWeek(player, week, Option(season)))
            }
          }
        }
      }
    }

}
