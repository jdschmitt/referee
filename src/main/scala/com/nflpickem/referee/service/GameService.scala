package com.nflpickem.referee.service

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Game
import scalikejdbc._

/**
  * Created by jason on 3/3/17.
  */
object GameService extends Whistle {

  def insertGame(game: Game): Game = DB.autoCommit { implicit session =>
    val insertStmt =
      sql"""
          INSERT INTO Game(
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

  def getGamesForWeek(week: Int): Seq[Game] = DB.readOnly { implicit session =>
    // TODO Probably should figure out how to avoid this extra DB query
    val seasonId: Long = SeasonService.currentSeason.get.id.get
    val selectStmt = sql"SELECT * FROM game WHERE season_id = $seasonId AND week_number = $week"
    selectStmt.map(Game.fromDb).list().apply()
  }

  def deleteGame(id: Long): Boolean = DB.autoCommit { implicit session =>
    sql"DELETE FROM game WHERE id = $id;"
      .update().apply() == 1
  }

  def getGame(id: Long): Option[Game] = DB.readOnly { implicit session =>
    sql"SELECT * FROM game WHERE id = $id"
        .map(Game.fromDb).single().apply()
  }

  def getGamesForSeason(seasonId: Long): Seq[Game] = DB.readOnly { implicit session =>
    sql"SELECT * FROM game WHERE season_id = $seasonId ORDER BY game_time ASC;"
        .map(Game.fromDb).list().apply()
  }

}
