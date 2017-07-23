package com.nflpickem.referee.util

import com.nflpickem.referee.model.Season
import com.nflpickem.referee.service.CurrentWeek
import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by jason on 7/21/17.
  */
class WeekHelperTest extends FlatSpec with Matchers {
  val helper: WeekHelper = new WeekHelper(TestRefereeClock)

  "A helper" should "get return current week is 1 before season starts" in {
    val season: Season = Season(Some(1), new DateTime(2016, 9, 8, 0, 0), new DateTime(2017, 1, 5, 0, 0), new DateTime(2017, 2, 2, 0, 0 ))

    TestRefereeClock.setNow(season.firstRegularGameDate.minusMonths(1))
    helper.currentWeek(season) shouldEqual CurrentWeek(1)
  }

  it should "get correct week 2" in {
    val season: Season = Season(Some(1), new DateTime(2016, 9, 8, 0, 0), new DateTime(2017, 1, 5, 0, 0), new DateTime(2017, 2, 2, 0, 0 ))

    TestRefereeClock.setNow(season.firstRegularGameDate.plusDays(8))
    helper.currentWeek(season) shouldEqual CurrentWeek(2)
  }

  it should "get correct week 18" in {
    val season: Season = Season(Some(1), new DateTime(2016, 9, 8, 0, 0), new DateTime(2017, 1, 5, 0, 0), new DateTime(2017, 2, 2, 0, 0 ))

    TestRefereeClock.setNow(season.firstPlayoffGameDate.plusDays(1))
    helper.currentWeek(season) shouldEqual CurrentWeek(18)
  }

  it should "get correct week 19" in {
    val season: Season = Season(Some(1), new DateTime(2016, 9, 8, 0, 0), new DateTime(2017, 1, 5, 0, 0), new DateTime(2017, 2, 2, 0, 0 ))

    TestRefereeClock.setNow(season.superBowlDate.minusDays(4))
    helper.currentWeek(season) shouldEqual CurrentWeek(19)
  }

  it should "get correct first day of the week when given a Tuesday" in {
    val now: DateTime = new DateTime(2017, 9, 5, 0, 0)    // This is a Tuesday already
    val tues: DateTime = helper.firstDayOfWeek(now)

    tues.dayOfYear().get() shouldEqual now.dayOfYear().get()
  }

  it should "get correct first day of the week when given a Wednesday" in {
    val now: DateTime = new DateTime(2017, 9, 6, 0, 0)        // This is a Wednesday
    val expected: DateTime = new DateTime(2017, 9, 5, 0, 0)   // Tuesday previous
    val tues: DateTime = helper.firstDayOfWeek(now)

    tues.dayOfYear().get() shouldEqual expected.dayOfYear().get()
  }

  it should "get correct first day of the week when given a Monday" in {
    val now: DateTime = new DateTime(2017, 9, 11, 0, 0)        // This is a Monday
    val expected: DateTime = new DateTime(2017, 9, 5, 0, 0)   // Tuesday previous
    val tues: DateTime = helper.firstDayOfWeek(now)

    tues.dayOfYear().get() shouldEqual expected.dayOfYear().get()
  }

}
