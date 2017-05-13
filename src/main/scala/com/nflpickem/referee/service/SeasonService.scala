package com.nflpickem.referee.service

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Season
import org.joda.time._
import org.joda.time.base.AbstractPartial
import scalikejdbc._

/**
  * Created by jason on 2/23/17.
  */
object SeasonService extends Whistle {

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

      season.copy(id = Some(id))

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

  def deleteSeason(id: Long): Boolean = DB.autoCommit { implicit session =>
    val deleteStmt =
      sql"""
           DELETE FROM season WHERE id = $id;
         """
    deleteStmt.update().apply() == 1
  }

  def currentSeason: Option[Season] = DB.readOnly { implicit session =>
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

  def currentWeek: CurrentWeek = DB.readOnly { implicit session =>
    val seasonOpt = currentSeason
    if (seasonOpt.isEmpty) {
      log.error("Could not read current season from DB!!")
      return CurrentWeek(-1)
    }

    val season = seasonOpt.get

    val now = DateTime.now
    val firstDayOfSeason = firstDayOfWeek(season.firstRegularGameDate)

    if (now.isBefore(firstDayOfSeason))
      return CurrentWeek(1)

    // Subtract 1 week since Super Bowl week is really 2 weeks
    val firstDayOfSuperBowlWeek = firstDayOfWeek(season.superBowlDate.minusWeeks(1))
    if (now.isAfter(firstDayOfSuperBowlWeek))
      return CurrentWeek(19)

    val firstDayOfPlayoffsWeek = firstDayOfWeek(season.firstPlayoffGameDate)
    if (now.isAfter(firstDayOfPlayoffsWeek))
      return CurrentWeek(18)

    val daysBetween = Days.daysBetween(firstDayOfSeason, now).getDays + 1
    CurrentWeek(Math.ceil(daysBetween / 7).toInt)
  }

  // Returns previous Tuesday from given date
  def firstDayOfWeek(date: DateTime): DateTime = {
    val tuesPartial: AbstractPartial = new Partial().`with`(DateTimeFieldType.dayOfWeek(), DateTimeConstants.TUESDAY)

    val dt: DateTime = tuesPartial.toDateTime(date)

    def aux(dt: DateTime, input: DateTime): DateTime = {
      if (dt.isBefore(input)) {
        val newDt = dt.withFieldAdded(tuesPartial.getFieldType(0).getRangeDurationType, 1)
        aux(newDt, input)
      } else
        dt
    }

    aux(dt, date).withHourOfDay(10).withMinuteOfHour(0)
  }

}

case class CurrentWeek(currentWeek: Int)
