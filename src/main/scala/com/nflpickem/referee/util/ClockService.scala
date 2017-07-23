package com.nflpickem.referee.util

import org.joda.time.DateTime

/**
  * Created by jason on 7/21/17.
  */
trait ClockService {

  def now: DateTime

}
