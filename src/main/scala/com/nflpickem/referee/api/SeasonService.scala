package com.nflpickem.referee.api

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Season
import org.joda.time.base.AbstractPartial
import org.joda.time._
import scalikejdbc._

/**
  * Created by jason on 2/23/17.
  */
object SeasonService extends Whistle {

  def currentSeason: Option[Season] = DB.readOnly { implicit session =>
    // TODO Cache this query
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
      } else curSeason.get.id
    }

  def currentWeek: CurrentWeek = DB.readOnly { implicit session =>
    val seasonOpt = SeasonService.currentSeason
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
