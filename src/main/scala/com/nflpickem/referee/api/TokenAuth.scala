package com.nflpickem.referee.api

import com.nflpickem.referee.Referee
import com.nflpickem.referee.model.Player
import com.nflpickem.referee.service.PlayerService
import com.nflpickem.referee.util.ClockService
import org.joda.time.DateTime
import spray.routing.{Directive1, HttpService}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by jason on 7/10/17.
  */
trait TokenAuth extends HttpService {
  implicit def executionContext: ExecutionContextExecutor = actorRefFactory.dispatcher

  import net.codingwell.scalaguice.InjectorExtensions._
  val clock: ClockService = Referee.injector.instance[ClockService]

  private val authenticator = RefereeTokenAuthenticator[Player](
    headerName = "X-Auth-Token"
  ) { token: String =>
    Future {
      val now: DateTime = clock.now
      PlayerService.getPlayerByAuthToken(token, now)
    }
  }

  val tokenAuth: Directive1[Player] = authenticate(authenticator)

}
