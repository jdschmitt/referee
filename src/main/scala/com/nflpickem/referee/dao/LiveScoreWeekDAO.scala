package com.nflpickem.referee.dao

import com.nflpickem.referee.live.LiveScoreGame
import scalikejdbc._

/**
  * Created by jason on 7/16/17.
  */
object LiveScoreWeekDAO extends LiveScoreWeekDatabase {

  def insert(lg: LiveScoreGame): Boolean = DB.autoCommit { implicit session =>
    sql"""
         INSERT INTO live_score_game (game_dow, game_time, game_quarter, game_quarter_time, away_team, away_team_score,
         home_team, home_team_score, possession_team, in_red_zone, nfl_game_id, unknown_value, season_week, season_year,
         game_id)
         VALUES ($lg.gameDow, $lg.gameTime, $lg.gameQuarter, $lg.gameTime, $lg.awayTeam, $lg.awayTeamScore, $lg.homeTeam,
          $lg.homeTeamScore, $lg.possessionTeam, $lg.inRedZone, $lg.nflGameId, $lg.unknownValue, $lg.seasonWeek,
          $lg.seasonYear, $lg.lg.gameId)
       """.update().apply() == 1
  }

  def update(lg: LiveScoreGame): Boolean = DB.autoCommit { implicit session =>
    sql"""
         UPDATE live_score_game SET
          game_dow = $lg.gameDow,
          game_time = $lg.gameTime,
          game_quarter = $lg.gameQuarter,
          game_quarter_time = $lg.gameQuarterTime,
          away_team = $lg.awayTeam,
          away_team_score = $lg.awayTeamScore,
          home_team = $lg.homeTeam,
          home_team_score = $lg.homeTeamScore,
          possession_team = $lg.possessionTeam,
          in_red_zone = $lg.inRedZone,
          unknown_value = $lg.unknownValue,
          season_week = $lg.seasonWeek,
          season_year = $lg.seasonYear,
          game_id = $lg.gameId
          WHERE id = $lg.nflGameId
       """.update().apply() == 1
  }

  def upsert(lg:LiveScoreGame): LiveScoreGame = DB.autoCommit { implicit session =>
    ???
  }

  def get(id: String): Option[LiveScoreGame] = DB.readOnly { implicit session =>
    sql"SELECT * FROM live_score_game WHERE nfl_game_id = $id;"
      .map(LiveScoreGame.fromDb).single().apply()
  }

}
