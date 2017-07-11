package com.nflpickem.referee.api

import com.nflpickem.referee.model.Player
import com.nflpickem.referee.service.PlayerService
import spray.routing.{Directive1, HttpService}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by jason on 7/10/17.
  */
trait TokenAuth extends HttpService {
  implicit def executionContext: ExecutionContextExecutor = actorRefFactory.dispatcher

  private val authenticator = RefereeTokenAuthenticator[Player](
    headerName = "X-Auth-Token"
  ) { token: String =>
    Future {
      PlayerService.getPlayerByAuthToken(token)
    }
  }

  val tokenAuth: Directive1[Player] = authenticate(authenticator)

}
