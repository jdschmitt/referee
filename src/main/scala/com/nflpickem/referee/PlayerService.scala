package com.nflpickem.referee

import com.nflpickem.referee.model.{Player, RankedPlayer}
import scalikejdbc._

/**
  * Created by jason on 2/1/17.
  */
object PlayerService {

  def allPlayers: Seq[Player] = DB.readOnly { implicit session =>
    sql"SELECT * FROM player".map(Player.fromDb).list().apply()
  }

  def rankedPlayers: Seq[RankedPlayer] = DB.readOnly { implicit session =>
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
              GROUP BY
                pl.id
              ORDER BY
                total DESC
            ) totals, (SELECT @rank:=0, @row:=0, @l:=NULL) rank;
       """.map(RankedPlayer.fromDb).list().apply()
  }

}
