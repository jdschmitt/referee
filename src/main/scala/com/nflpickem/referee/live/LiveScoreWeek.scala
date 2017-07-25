package com.nflpickem.referee.live

import com.nflpickem.referee.model.GameType
import com.nflpickem.referee.util.Converters._
import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 7/16/17.
  */
case class LiveScoreWeek(ss: Seq[Seq[String]])

case class LiveScoreGame(gameDow: String, gameTime: String, gameQuarter: String, gameQuarterTime: String,
                         awayTeam: String, awayTeamScore: String, homeTeam: String, homeTeamScore: String,
                         possessionTeam: String, inRedZone: String, nflGameId: String, unknownValue: String,
                         seasonWeek: String, seasonYear: String, gameId: Option[Long]) {

  private lazy val weekParts: (String, Int) = {
    val Re = "(\\D*)(.*)".r
    val Re(a, b) = seasonWeek
    (a, b.toInt)
  }

  lazy val gameType: GameType = GameType(weekParts._1)

  lazy val weekNumber: Int = weekParts._2

  lazy val awayScore: Option[Int] = awayTeamScore
  lazy val homeScore: Option[Int] = homeTeamScore

  lazy val isFinalScore: Boolean = gameQuarter.toLowerCase() == "final"

}

object LiveScoreGame {
  def apply(s: Seq[String], gameId: Option[Long] = None): LiveScoreGame =
    LiveScoreGame(s.head, s(1), s(2), s(3), s(4), s(5), s(6), s(7), s(8), s(9), s(10), s(11), s(12), s(13), gameId)

  def fromDb(rs: WrappedResultSet): LiveScoreGame = {
    val dow = rs.string("game_dow")
    val time = rs.string("game_time")
    val quarter = rs.string("game_quarter")
    val quarterTime = rs.string("game_quarter_time")
    val awayTeam = rs.string("away_team")
    val awayScore = rs.string("away_team_score")
    val homeTeam = rs.string("home_team")
    val homeScore = rs.string("home_team_score")
    val possTeam = rs.string("possession_team")
    val inRedZone = rs.string("in_red_zone")
    val id = rs.string("nfl_game_id")
    val unknownValue = rs.string("unknown_value")
    val week = rs.string("season_week")
    val year = rs.string("season_year")
    val gId = rs.longOpt("game_id")

    LiveScoreGame(dow, time, quarter, quarterTime, awayTeam, awayScore, homeTeam, homeScore, possTeam, inRedZone, id,
      unknownValue, week, year, gId)
  }
}
