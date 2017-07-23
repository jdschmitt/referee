package com.nflpickem.referee.util

import org.joda.time.DateTime

/**
  * Created by jason on 7/21/17.
  */
object RefereeClock extends ClockService {

  override def now: DateTime = DateTime.now()

}
