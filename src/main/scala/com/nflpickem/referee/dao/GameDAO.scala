package com.nflpickem.referee.dao

import com.google.inject.{Inject, Singleton}
import com.nflpickem.referee.Whistle
import com.nflpickem.referee.live.LiveScoreGame
import com.nflpickem.referee.model.Game
import com.nflpickem.referee.service.GameService
import scalikejdbc._

/**
  * Created by jason on 3/3/17.
  */
@Singleton
class GameDAO @Inject()(gameService: GameService) extends GameDatabase with Whistle {

  def insertGame(game: Game): Game = DB.autoCommit { implicit session =>
    val insertStmt =
      sql"""
          INSERT INTO game(
            version,
            away_team,
            home_team,
            line,
            game_time,
            game_type,
            week_number,
            season_id
          ) VALUES (
            ${game.version},
            ${game.awayTeam.id},
            ${game.homeTeam.id},
            ${game.line},
            ${game.gameTime},
            ${game.gameType},
            ${game.weekNumber},
            ${game.seasonId}
          )
         """.stripMargin
    try {
      val id: Long = insertStmt.updateAndReturnGeneratedKey.apply()

      game.copy(id = Option(id))

    } catch {
      case t: Throwable => log.error("Error while inserting new game", t)
        println(s"error while inserting new game: ${t.getMessage}")
        game
    }
  }

  def updateGame(game: Game): Boolean = DB.autoCommit { implicit session =>
    require(game.id.isDefined)
    sql"""
          UPDATE game
           SET away_score = $game.awayScore,
            away_team = $game.awayTeam.id,
            home_score = $game.homeScore,
            home_team = $game.homeTeam.id,
            game_time = $game.gameTime,
            line = $game.line,
            offensive_yards = $game.offensiveYards,
            over_under = $game.overUnder,
            game_type = $game.gameType
          WHERE id = $game.id.get
         """.update().apply() == 1
  }

  def getGamesForWeek(week: Int): Seq[Game] = DB.readOnly { implicit session =>
    // TODO Probably should figure out how to avoid this extra DB query
    val seasonId: Long = SeasonDAO.currentSeason.get.id.get
    val selectStmt = sql"SELECT * FROM game WHERE season_id = $seasonId AND week_number = $week"
    selectStmt.map(gameService.fromDb).list().apply()
  }

  def deleteGame(id: Long): Boolean = DB.autoCommit { implicit session =>
    sql"DELETE FROM game WHERE id = $id;"
      .update().apply() == 1
  }

  def getGame(id: Long): Option[Game] = DB.readOnly { implicit session =>
    sql"SELECT * FROM game WHERE id = $id"
        .map(gameService.fromDb).single().apply()
  }

  def getGamesForSeason(seasonId: Long): Seq[Game] = DB.readOnly { implicit session =>
    sql"SELECT * FROM game WHERE season_id = $seasonId ORDER BY game_time ASC;"
        .map(gameService.fromDb).list().apply()
  }

  def getGameFromLiveScoreGame(lsg: LiveScoreGame): Option[Game] = DB.readOnly { implicit session =>
    sql"""
          SELECT g.* FROM game g
          JOIN team ta ON ta.id = g.away_team
          JOIN team th ON th.id = g.home_team
          JOIN season s ON s.id = g.season_id
          WHERE ta.abbreviation = $lsg.awayTeam AND th.abbreviation = $lsg.homeTeam
            AND YEAR(s.first_reg_game_date) = $lsg.seasonYear
            AND g.week_number = $lsg.weekNumber
      """.map(gameService.fromDb).single().apply()
  }

}
