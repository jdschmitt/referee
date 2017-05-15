package com.nflpickem.referee.service

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.{AuthToken, Role}
import io.github.nremond.SecureHash
import scalikejdbc._

/**
  * Created by jason on 2/1/17.
  */
object AuthService extends Whistle {

  def addRole(role: Role): Role = DB.autoCommit { implicit session =>
    val stmt = sql"INSERT INTO role (version, authority) VALUES (${role.version}, ${role.authority});"

    try {
      val id: Long = stmt.updateAndReturnGeneratedKey.apply()

      role.copy(id = Some(id))

    } catch {
      case t: Throwable => log.error("Error while inserting new role", t)
        println(s"error while inserting new role: ${t.getMessage}")
        role
    }
  }

  def getRoles: Seq[Role] = DB.readOnly { implicit session =>
    sql"SELECT * FROM role;".map(Role.fromDb).list().apply()
  }

  def addAuthToken(token: AuthToken): AuthToken = DB.autoCommit { implicit session =>
    val stmt =
      sql"""
            INSERT INTO auth_token
              (player_id, token, expiration)
            VALUES
              (${token.playerId}, ${token.token}, ${token.expiration});
         """.stripMargin
    try {
      val id: Long = stmt.updateAndReturnGeneratedKey.apply()

      token.copy(id = Some(id))
    } catch {
      case t: Throwable => log.error("Error while inserting new auth token", t)
        println(s"error while inserting new auth token: ${t.getMessage}")
        token
    }
  }

  def isAuthTokenValid(playerId: Long, authToken: String): Boolean = DB.readOnly { implicit session =>
    val token: Option[AuthToken] =
      sql"SELECT * FROM auth_token WHERE player_id = $playerId AND token = $authToken"
        .map(AuthToken.fromDb).single().apply()

    token.isDefined && token.get.expiration.isAfterNow
  }

  def hashedPassword(password: String): String = {
    SecureHash.createHash(password)
  }

  def validatePassword(password: String, hashedPassword: String): Boolean = {
    SecureHash.validatePassword(password, hashedPassword)
  }

}
