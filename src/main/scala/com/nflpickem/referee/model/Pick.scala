package com.nflpickem.referee.model

import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 4/2/17.
  */
case class Pick(id: Long, gameId: Long, version: Int, isCorrect: Option[Boolean], offensiveYards: Option[Float],
                overUnder: Option[Float], playerId: Long, teamId: Int)

object Pick {
  def fromDb(rs:WrappedResultSet): Pick = {
    val id: Long = rs.long("id")
    val gameId: Long = rs.long("game_id")
    val version: Int = rs.int("version")
    val isCorrect: Option[Boolean] = rs.booleanOpt("is_correct")
    val offensiveYards: Option[Float] = rs.floatOpt("offensive_yards")
    val overUnder: Option[Float] = rs.floatOpt("over_under")
    val playerId: Long = rs.long("player_id")
    val teamId: Int = rs.int("team_id")
    Pick(id, gameId, version, isCorrect, offensiveYards, overUnder, playerId, teamId)
  }

  def getTeam(rs: WrappedResultSet, prefix: String): Option[Team] = {
    Option(Team.fromDb(rs, prefix, s"${prefix}_abbreviation", s"${prefix}_market", s"${prefix}_mascot"))
  }
}