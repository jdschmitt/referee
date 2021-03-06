package com.nflpickem.referee.util

import com.google.inject.{Guice, Inject}
import com.google.inject.testing.fieldbinder.{Bind, BoundFieldModule}
import com.nflpickem.referee.dao.CurrentWeek
import com.nflpickem.referee.model.Season
import org.joda.time.DateTime
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by jason on 7/21/17.
  */
class WeekHelperTest extends FlatSpec with Matchers with BeforeAndAfter {
  @Bind val testClock: ClockService = TestRefereeClock

  @Inject var helper: WeekHelper = _

  before {
    Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this)
  }

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

  it should "get date from week 1 and day of week Thu" in {
    val season: Season = Season(Some(1), new DateTime(2016, 9, 8, 0, 0), new DateTime(2017, 1, 5, 0, 0), new DateTime(2017, 2, 2, 0, 0 ))

    val d: DateTime = helper.dateFromWeekAndDay(1, "Thu", season)

    val expected: DateTime = new DateTime(2016, 9, 8, 0, 0)
    d.dayOfYear().get() shouldEqual expected.dayOfYear().get()
  }

  it should "get date from week 2 and day of week Sun" in {
    val season: Season = Season(Some(1), new DateTime(2016, 9, 8, 0, 0), new DateTime(2017, 1, 5, 0, 0), new DateTime(2017, 2, 2, 0, 0 ))

    val d: DateTime = helper.dateFromWeekAndDay(2, "Sun", season)

    val expected: DateTime = new DateTime(2016, 9, 18, 0, 0)
    d.dayOfYear().get() shouldEqual expected.dayOfYear().get()
  }

}
