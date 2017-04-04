package com.nflpickem.referee.model

import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 4/2/17.
  */
case class Pick(id: Long, gameId: Long, version: Int, isCorrect: Option[Boolean], offensiveYards: Option[Float],
                overUnder: Option[Float], team: Option[Team], gameTime: DateTime, homeTeam: Team, awayTeam: Team,
                line: Float, gameType: GameType)

object Pick {
  def fromDb(rs:WrappedResultSet): Pick = {
    val id: Long = rs.long("id")
    val gameId: Long = rs.long("game_id")
    val version: Int = rs.int("version")
    val isCorrect: Option[Boolean] = rs.booleanOpt("is_correct")
    val offensiveYards: Option[Float] = rs.floatOpt("offensive_yards")
    val overUnder: Option[Float] = rs.floatOpt("over_under")
    val team: Option[Team] = rs.stringOpt("pick").flatMap(_ => getTeam(rs, "pick"))
    val gameTime: DateTime = rs.jodaDateTime("game_time")
    // TODO revisit this. Just calling 'get' here seems risky but it _should_ always work
    val homeTeam: Team = getTeam(rs, "home_team").get
    val awayTeam: Team = getTeam(rs, "away_team").get
    val line: Float = rs.long("line")
    val gameType: GameType = GameType(rs.string("game_type"))
    Pick(id, gameId, version, isCorrect, offensiveYards, overUnder, team, gameTime, homeTeam, awayTeam, line, gameType)
  }

  def getTeam(rs: WrappedResultSet, prefix: String): Option[Team] = {
    Some(Team.fromDb(rs, prefix, s"${prefix}_abbreviation", s"${prefix}_market", s"${prefix}_mascot"))
  }
}