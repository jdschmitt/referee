package com.nflpickem.referee.service

import com.google.inject.{Inject, Singleton}
import com.nflpickem.referee.Whistle
import com.nflpickem.referee.dao.{SeasonDatabase, TeamDatabase}
import com.nflpickem.referee.live.LiveScoreGame
import com.nflpickem.referee.model.{Game, Season, Team}
import com.nflpickem.referee.util.WeekHelper
import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 8/5/17.
  */
@Singleton
class GameService @Inject()(teamDb: TeamDatabase, seasonDb: SeasonDatabase, weekHelper: WeekHelper) extends Whistle {

  def fromDb(rs:WrappedResultSet): Game = {
    val id: Long = rs.long("id")
    val version: Long = rs.long("version")
    val gameTime: DateTime = rs.jodaDateTime("game_time")
    val awayScore: Option[Int] = rs.intOpt("away_score")
    val awayTeam: Team = teamDb.getById(rs.int("away_team")).get
    val homeScore: Option[Int] = rs.intOpt("home_score")
    val homeTeam: Team = teamDb.getById(rs.int("home_team")).get
    val line: Float = rs.float("line")
    val offensiveYards: Option[Float] = rs.floatOpt("offensive_yards")
    val overUnder: Option[Float] = rs.floatOpt("over_under")
    val weekNumber: Int = rs.int("week_number")
    val gameType: String = rs.string("game_type")
    val seasonId: Long = rs.long("season_id")

    Game(Option(id), version, gameTime, awayScore, awayTeam, homeScore, homeTeam, Option(line), offensiveYards, overUnder,
      weekNumber, gameType, seasonId)
  }
  
  def gameFromLiveGame(liveGame: LiveScoreGame): Game = {
    try {
      val awayTeam: Option[Team] = teamDb.getForAbbreviation(liveGame.awayTeam)
      require(awayTeam.isDefined)
      val homeTeam: Option[Team] = teamDb.getForAbbreviation(liveGame.homeTeam)
      require(homeTeam.isDefined)

      val season: Option[Season] = seasonDb.getSeasonByYear(liveGame.seasonYear.toInt)
      require(season.isDefined)

      val week: Int = liveGame.weekNumber
      val gameTime: DateTime = weekHelper.dateFromWeekAndDay(week, liveGame.gameDow, season.get)

      Game(None, 1, gameTime, liveGame.awayScore, awayTeam.get, liveGame.homeScore, homeTeam.get, None, None, None,
        week, liveGame.gameType.id, season.get.id.get)
    } catch {
      case t: Throwable => log.error("Failed to convert live game to game.", t)
        throw t
    }
  }

}
