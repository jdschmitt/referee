package com.nflpickem.referee.service

import com.nflpickem.referee.model.{Pick, Player, SubmitPick}
import scalikejdbc._

/**
  * Created by jason on 4/2/17.
  */
object PickService {

  def picksForWeek(player: Player, weekNumber: Int, season: Option[Long] = None): Seq[Pick] = DB.readOnly { implicit session =>
    // TODO Probably should figure out how to avoid this extra DB query
    val seasonId: Long = season.getOrElse(SeasonService.currentSeason.get.id.get)
    val playerId: Long = player.id.get
    val selectStmt = sql"""
        SELECT * FROM pick p
        JOIN game g ON g.id = p.game_id
        WHERE p.player_id = $playerId AND g.season_id = $seasonId AND g.week_number = $weekNumber
      """.stripMargin
    selectStmt.map(Pick.fromDb).list().apply()
  }

  def submitPicks(picks: Seq[SubmitPick]): Boolean = ???

}
