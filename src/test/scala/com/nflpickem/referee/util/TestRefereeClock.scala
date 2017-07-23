package com.nflpickem.referee.util

import org.joda.time.DateTime

/**
  * Created by jason on 7/21/17.
  */
object TestRefereeClock extends ClockService {

  var fakeNow: Option[DateTime] = None

  override def now: DateTime = fakeNow match {
    case Some(n) => n
    case None => RefereeClock.now
  }

  def setNow(newNow: DateTime): Unit = {
    fakeNow = Option(newNow)
  }

}
