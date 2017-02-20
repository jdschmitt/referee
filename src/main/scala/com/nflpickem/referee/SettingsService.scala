package com.nflpickem.referee

import com.nflpickem.referee.model.{Season, Settings}
import org.joda.time._
import org.joda.time.base.AbstractPartial
import scalikejdbc._

/**
  * Created by jason on 2/1/17.
  */
object SettingsService {

  def get: Settings = DB.readOnly { implicit session =>
    sql"select * from settings".map(Settings.fromDb).list().apply().head
  }

  def currentWeek: CurrentWeek = DB.readOnly { implicit session =>
    val season = sql"""
      SELECT * FROM season a
      JOIN settings b on a.id = b.current_season_id
    """.map(Season.fromDb).list().apply().head

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
