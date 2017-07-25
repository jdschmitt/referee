package com.nflpickem.referee.util

import com.nflpickem.referee.model.Season
import com.nflpickem.referee.service.{CurrentWeek, SeasonService}
import org.joda.time._

/**
  * Created by jason on 7/21/17.
  */
class WeekHelper(clock: ClockService = RefereeClock) {

  // TODO - Not this most efficient way to do this but it works for now
  def firstDayOfWeek(date: DateTime): DateTime = {
    if (date.dayOfWeek().get() != DateTimeConstants.TUESDAY)
      firstDayOfWeek(date.minusDays(1))
    else
      date
  }

  def firstDayOfWeek(date: DateTime, dowConst: Int): DateTime = {
    if (date.dayOfWeek().get() != dowConst)
      firstDayOfWeek(date.minusDays(1), dowConst)
    else
      date
  }

  def currentWeek(season: Season): CurrentWeek = {
    val now = clock.now
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
    CurrentWeek(Math.ceil(daysBetween / 7.0).toInt)
  }

  def currentWeek: Option[CurrentWeek] = {
    SeasonService.currentSeason match {
      case Some(season) => Option(currentWeek(season))
      case None => None
    }
  }

  def dateFromWeekAndDay(week: Int, dowStr: String, season: Season): DateTime = {
    require(week >= 1 && week <= 19)

    // Get constant from dow string
    val dow: Int = dowFromString(dowStr).getOrElse(-1)
    require(dow > 0)

    val firstDayOfSeason: DateTime = firstDayOfWeek(season.firstRegularGameDate)

    // Tuesday of the week in question
    val firstDow = firstDayOfSeason.plusDays((week - 1) * 7)

    def dayInc(d: DateTime, dowConst: Int): DateTime = {
      if (d.dayOfWeek().get() != dowConst)
        dayInc(d.plusDays(1), dowConst)
      else
        d
    }

    dayInc(firstDow, dow)
  }

  val dowMap: Map[String, Int] = Map(
    ("MON", DateTimeConstants.MONDAY),
    ("MONDAY", DateTimeConstants.MONDAY),
    ("TUE", DateTimeConstants.TUESDAY),
    ("TUESDAY", DateTimeConstants.TUESDAY),
    ("WED", DateTimeConstants.WEDNESDAY),
    ("WEDNESDAY", DateTimeConstants.WEDNESDAY),
    ("THU", DateTimeConstants.THURSDAY),
    ("THURSDAY", DateTimeConstants.THURSDAY),
    ("FRI", DateTimeConstants.FRIDAY),
    ("FRIDAY", DateTimeConstants.FRIDAY),
    ("SAT", DateTimeConstants.SATURDAY),
    ("SATURDAY", DateTimeConstants.SATURDAY),
    ("SUN", DateTimeConstants.SUNDAY),
    ("SUNDAY", DateTimeConstants.SUNDAY)
  )

  def dowFromString(dow: String): Option[Int] = Option(dowMap(dow.toUpperCase()))

}

object WeekHelper {
  def apply(clock: ClockService = RefereeClock): WeekHelper = new WeekHelper(clock)
}
