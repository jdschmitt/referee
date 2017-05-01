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

      game.copy(id = Some(id))

    } catch {
      case t: Throwable => log.error("Error while inserting new game", t)
        println(s"error while inserting new game: ${t.getMessage}")
        game
    }
  }

  def getGamesForWeek(week: Int): Seq[Game] = DB.readOnly { implicit session =>
    val selectStmt =
      sql"""
            SELECT g.* FROM GAME g
            JOIN settings s ON s.id = g.season_id
            WHERE week_number = $week
         """.stripMargin
    selectStmt.map(Game.fromDb).list().apply()
  }

}
