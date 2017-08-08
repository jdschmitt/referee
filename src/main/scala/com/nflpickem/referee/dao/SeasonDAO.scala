package com.nflpickem.referee.dao

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Season
import scalikejdbc._

/**
  * Created by jason on 2/23/17.
  */
object SeasonDAO extends SeasonDatabase with Whistle {

  def getSeasons: Seq[Season] = DB.readOnly { implicit session =>
    sql"""
       SELECT * FROM season;
    """.map(Season.fromDb).list().apply()
  }

  def insertSeason(season: Season): Season = DB.autoCommit { implicit session =>
    val insertStmt =
      sql"""
          INSERT INTO season(
            first_reg_game_date,
            first_playoff_game_date,
            super_bowl_date
          ) VALUES (
            ${season.firstRegularGameDate},
            ${season.firstPlayoffGameDate},
            ${season.superBowlDate}
          );
         """.stripMargin
    try {
      val id: Long = insertStmt.updateAndReturnGeneratedKey.apply()

      season.copy(id = Option(id))

    } catch {
      case t: Throwable => log.error("Error while inserting new season", t)
        println(s"error while inserting new season: ${t.getMessage}")
        season
    }
  }

  def getSeason(id: Long): Option[Season] = DB.readOnly { implicit session =>
    sql"SELECT * FROM season WHERE id = $id"
      .map(Season.fromDb).single().apply()
  }

  def getSeasonByYear(year: Int): Option[Season] = DB.readOnly { implicit session =>
    sql"SELECT * FROM season WHERE YEAR(first_reg_game_date) = $year;"
      .map(Season.fromDb).single().apply()
  }

  def deleteSeason(id: Long): Boolean = DB.autoCommit { implicit session =>
    val deleteStmt =
      sql"""
           DELETE FROM season WHERE id = $id;
         """
    deleteStmt.update().apply() == 1
  }

  def currentSeason: Option[Season] = DB.readOnly { implicit session =>
    // TODO insert cache here (Guava?)
    sql"""
      SELECT * FROM season ORDER BY first_reg_game_date DESC LIMIT 1;
    """.map(Season.fromDb).single().apply()
  }

  def currentSeasonId(seasonIdOpt: Option[Long]): Long =
    if (seasonIdOpt.isDefined) seasonIdOpt.get
    else {
      val curSeason: Option[Season] = currentSeason
      if (curSeason.isEmpty) {
        log.error("Could not read current season from DB!!")
        -1
      } else curSeason.get.id.get
    }

}

case class CurrentWeek(currentWeek: Int)
