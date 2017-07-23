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

}

object WeekHelper {
  def apply(clock: ClockService = RefereeClock): WeekHelper = new WeekHelper(clock)
}
