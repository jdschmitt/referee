package com.nflpickem.referee.service

import org.joda.time.DateTime

/**
  * Created by jason on 7/11/17.
  */
trait ClockService {

  def now: DateTime

}

object ClockFactory {

  def getClockService: ClockService = RefereeClock

}

object RefereeClock extends ClockService {

  override def now: DateTime = DateTime.now()

}