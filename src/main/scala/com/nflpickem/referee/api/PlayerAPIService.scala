package com.nflpickem.referee.api

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.api.LeaguePasswordAuthenticator.SignUpPasswordExtraction.SignUpPassword
import com.nflpickem.referee.model.PlayerSignUp
import com.nflpickem.referee.service.{PlayerService, SettingsService}
import spray.http.StatusCodes
import spray.routing._

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by jason on 2/1/17.
  */
trait PlayerAPIService extends HttpService with Whistle {
  implicit def executionContext: ExecutionContextExecutor = actorRefFactory.dispatcher

  private val authenticator = LeaguePasswordAuthenticator[SignUpPassword] { signUpPassword: SignUpPassword =>
    Future {
      val actual: SignUpPassword = SettingsService.getSettings.map(_.leaguePassword).get
      if (signUpPassword == actual)
        Option(signUpPassword)
      else
        None
    }
  }

  def auth: Directive1[SignUpPassword] = authenticate(authenticator)

  import com.nflpickem.referee.model.ApiFormats._
  import spray.httpx.SprayJsonSupport._

  def playersRoute: Route =
    pathPrefix("api") {
      pathPrefix("players") {
        pathEndOrSingleSlash {
          get {
            complete {
              PlayerService.allPlayers
            }
          } ~
          post {
            authenticate(authenticator) { signUpPassword: SignUpPassword =>
              entity(as[PlayerSignUp]) { signUp: PlayerSignUp =>
                complete {
                  PlayerService.insertPlayer(signUp)
                }
              }
            }
          }
        } ~
        path(LongNumber) { id =>
          delete {
            complete {
              if (PlayerService.deletePlayer(id))
                StatusCodes.NoContent
              else
                StatusCodes.NotFound
            }
          }
        }
      } ~
      path("mainPotRanking") {
        get {
          complete(PlayerService.mainPotRanking())
        }
      } ~
      path("secondPotRanking") {
        get {
          complete(PlayerService.secondPotRanking())
        }
      }
    }

}
