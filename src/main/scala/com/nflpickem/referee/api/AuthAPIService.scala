package com.nflpickem.referee.api

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.{PlayerRole, Role, Token}
import com.nflpickem.referee.service.AuthService
import spray.http.StatusCodes
import spray.routing.authentication.BasicAuth
import spray.routing.{Directive1, HttpService, Route}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by jason on 2/1/17.
  */
trait AuthAPIService extends HttpService with Whistle {
  implicit def executionContext: ExecutionContextExecutor = actorRefFactory.dispatcher

  import com.nflpickem.referee._

  private val authenticator = RefereeTokenAuthenticator[String](
    headerName = "admin-token"
  ) { token: String =>
    Future {
      if (token == RefereeEnvConfig.getString("admin-token"))
        Some(token)
      else
        None
    }
  }

  val adminTokenAuth: Directive1[String] = authenticate(authenticator)

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def authRoute: Route =
    pathPrefix("api") {
      path("playerRole") {
        post {
          adminTokenAuth { _ =>
            entity(as[PlayerRole]) { playerRole: PlayerRole =>
              complete {
                AuthService.addPlayerRole(playerRole)
                StatusCodes.NoContent
              }
            }
          }
        }
      } ~
      path("roles") {
        post {
          adminTokenAuth { _ =>
            entity(as[Role]) { role =>
              complete {
                AuthService.addRole(role)
              }
            }
          }
        } ~
        get {
          complete(AuthService.getRoles)
        }
      } ~
      path("login") {
        post {
          authenticate(BasicAuth(RefereeUserPassAuthenticator, realm = "referee")) { token =>
            complete(token)
          }
        }
      } ~
      path("logout") {
        post {
          entity(as[Token]) { token: Token =>
            complete {
              AuthService.deleteAuthToken(token)
              StatusCodes.NoContent
            }
          }
        }
      }
    }
}
