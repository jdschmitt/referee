package com.nflpickem.referee.api

import java.util.UUID

import com.nflpickem.referee.model.AuthToken
import com.nflpickem.referee.service.{AuthService, PlayerService}
import org.joda.time.DateTime
import spray.routing.authentication.{UserPass, UserPassAuthenticator}

import scala.concurrent.Future

/**
  * Created by jason on 5/14/17.
  */
object RefereeUserPassAuthenticator extends UserPassAuthenticator[AuthTokenResponse] {

  override def apply(creds: Option[UserPass]): Future[Option[AuthTokenResponse]] = Future.successful {
    if (creds.isEmpty)
      None
    else {
      val up: UserPass = creds.get
      val player = PlayerService.getPlayer(up.user)
      val hashedPassword: Option[String] = PlayerService.getHashedPassword(up.user)

      if (player.isDefined && hashedPassword.isDefined && AuthService.validatePassword(up.pass, hashedPassword.get)) {
        val newToken = AuthToken(playerId = player.get.id.get, token = UUID.randomUUID().toString, expiration = DateTime.now())
        AuthService.addAuthToken(newToken)
        Some(AuthTokenResponse(newToken.token))
      } else
        None
    }
  }

}

case class AuthTokenResponse(token: String)