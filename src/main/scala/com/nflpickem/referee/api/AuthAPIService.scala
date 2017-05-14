package com.nflpickem.referee.api

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Role
import com.nflpickem.referee.service.AuthService
import spray.routing.{HttpService, Route}

/**
  * Created by jason on 2/1/17.
  */
trait AuthAPIService extends HttpService with Whistle {

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def authRoute: Route =
    pathPrefix("api") {
      pathPrefix("roles") {
        pathEnd {
          post {
            entity(as[Role]) { role =>
              complete {
                AuthService.addRole(role)
              }
            }
          } ~
          get {
            complete(AuthService.getRoles)
          }
        }
      }
    }
}
