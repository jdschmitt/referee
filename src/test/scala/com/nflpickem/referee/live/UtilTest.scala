package com.nflpickem.referee.live

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by jason on 7/16/17.
  */
class UtilTest extends FlatSpec with Matchers {

  "A live.Util" should "cleanup JSON" in {
    val sampleJson: String = "{\"ss\":[[\"Thu\",\"20:00:00\",\"Pregame\",,\"DAL\",,\"ARI\",,,,\"57169\",,\"PRE0\",\"2017\"]]}"
    sampleJson.indexOf(",,") should be > 0
    
    val newJson: String = Util.jsonCleaner(sampleJson)
    newJson.indexOf(",,") should be (-1)
  }

}
