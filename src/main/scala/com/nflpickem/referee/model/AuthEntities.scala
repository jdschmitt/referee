package com.nflpickem.referee.model

import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 5/14/17.
  */
case class Role(id: Option[Long] = None, version: Long, authority: String)

object Role {
  def fromDb(rs: WrappedResultSet): Role = {
    val id: Long = rs.long("id")
    val version: Long = rs.long("version")
    val authority: String = rs.string("authority")
    Role(Some(id), version, authority)
  }
}

case class AuthToken(id: Option[Long] = None, playerId: Long, token: String, expiration: DateTime)

object AuthToken {
  def fromDb(rs: WrappedResultSet): AuthToken = {
    def id: Long = rs.long("id")
    def playerId: Long = rs.long("player_id")
    def token: String = rs.string("token")
    def expiration: DateTime = rs.jodaDateTime("expiration")
    AuthToken(Some(id), playerId, token, expiration)
  }
}
