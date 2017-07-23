package com.nflpickem.referee.live

/**
  * Created by jason on 7/16/17.
  */
object Util {

  // JSON coming back from the NFL.com file is not proper JSON. Clean it up for consumption.
  def jsonCleaner(json: String): String = {
    if (json.indexOf(",,") > 0)
      Util.jsonCleaner(json.replaceAll(",,", ",\"\","))
    else
      json
  }

}
