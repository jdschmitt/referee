package com.nflpickem.referee.service

import com.nflpickem.referee.api.SeasonService
import com.nflpickem.referee.model.{Player, RankedPlayer}
import scalikejdbc._

/**
  * Created by jason on 2/1/17.
  */
object PlayerService {

  def allPlayers: Seq[Player] = DB.readOnly { implicit session =>
    sql"SELECT * FROM player".map(Player.fromDb).list().apply()
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
