package com.nflpickem.referee.api

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Player
import com.nflpickem.referee.service.PickService
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
            parameters('week.as[Int]) { week: Int =>
              if (week < 1 || week > 19)
                throw new IllegalArgumentException("Week must be a value 1 - 19")
              complete(PickService.picksForWeek(player, week))
            }
          }
        }
      }
    }

}
