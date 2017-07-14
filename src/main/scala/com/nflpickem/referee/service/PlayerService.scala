package com.nflpickem.referee.service

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.{Player, PlayerSignUp, RankedPlayer}
import org.joda.time.DateTime
import scalikejdbc._

/**
  * Created by jason on 2/1/17.
  */
object PlayerService extends Whistle {

  def allPlayers: Seq[Player] = DB.readOnly { implicit session =>
    sql"SELECT * FROM player".map(Player.fromDb).list().apply()
  }

  def insertPlayer(player: PlayerSignUp): Player = DB.autoCommit { implicit session =>
    val passDb = AuthService.hashedPassword(player.password)
    val stmt = sql"""
          INSERT INTO player
            (version, default_pick, email, enabled, first_name, last_name, password, password_expired)
          VALUES
            (1, ${player.defaultPick}, ${player.email}, 1, ${player.firstName}, ${player.lastName}, $passDb, 0)
      """.stripMargin

    try {
      val id: Long = stmt.updateAndReturnGeneratedKey.apply()

      Player(Option(id), player)
    } catch {
      case t: Throwable => log.error("Error while inserting new player", t)
        println(s"error while inserting new player: ${t.getMessage}")
        Player(None, player)
    }
  }

  def deletePlayer(id: Long): Boolean = DB.autoCommit { implicit session =>
    sql"DELETE FROM player WHERE id = $id"
      .update().apply() == 1
  }

  def getPlayer(email: String): Option[Player] = DB.readOnly { implicit session =>
    sql"SELECT * FROM player WHERE email = $email".map(Player.fromDb).single().apply()
  }

  def getPlayer(id: Long): Option[Player] = DB.readOnly { implicit session =>
    sql"SELECT * FROM player WHERE id = $id".map(Player.fromDb).single().apply()
  }

  def getPlayerByAuthToken(token: String, now: DateTime): Option[Player] = DB.readOnly { implicit session =>
    sql"""
        SELECT p.* FROM player p
        JOIN auth_token at ON at.player_id = p.id
        WHERE at.token = $token AND at.expiration > $now;
      """.map(Player.fromDb).single().apply()
  }

  def getHashedPassword(email: String): Option[String] = DB.readOnly { implicit session =>
    sql"SELECT password FROM player WHERE email = $email;"
      .map(_.string("password")).single().apply()
  }

  def mainPotRanking(seasonIdOpt: Option[Long] = None): Seq[RankedPlayer] = DB.readOnly { implicit session =>

    val seasonId: Long = SeasonService.currentSeasonId(seasonIdOpt)

    sql"""
       SELECT
         id, first_name, last_name,
         CASE WHEN @l=total THEN @rank ELSE @rank := @row + 1 END AS rank,
         @l:=total                                                AS total,
         @row:=@row + 1                                           AS row
       FROM (
              SELECT
                pl.id, pl.first_name, pl.last_name, sum(pi.is_correct) AS total
              FROM
                player AS pl
                JOIN
                  pick AS pi ON pi.player_id = pl.id
                JOIN
                  game AS ga ON ga.id = pi.game_id AND ga.season_id = $seasonId
              GROUP BY
                pl.id
              ORDER BY
                total DESC
            ) totals, (SELECT @rank:=0, @row:=0, @l:=NULL) rank;
    """.map(RankedPlayer.fromDb).list().apply()
  }

  def secondPotRanking(seasonIdOpt: Option[Long] = None): Seq[RankedPlayer] = DB.readOnly { implicit session =>
    val seasonId: Long = SeasonService.currentSeasonId(seasonIdOpt)

    sql"""
       SELECT
         id, first_name, last_name,
         CASE WHEN @l=total THEN @rank ELSE @rank := @row + 1 END AS rank,
         @l:=total                                                AS total,
         @row:=@row + 1                                           AS row
       FROM (
              SELECT
                pl.id, pl.first_name, pl.last_name, sum(pi.is_correct) AS total
              FROM
                player AS pl
                JOIN
                  pick AS pi ON pi.player_id = pl.id
                JOIN
                  game AS ga ON ga.id = pi.game_id AND ga.season_id = $seasonId
                JOIN
                  settings AS se ON se.season_id = ga.season_id
              WHERE
                ga.week_number >= se.second_pot_start_week
              GROUP BY
                pl.id
              ORDER BY
                total DESC
            ) totals, (SELECT @rank:=0, @row:=0, @l:=NULL) rank;
    """.map(RankedPlayer.fromDb).list().apply()
  }

}
