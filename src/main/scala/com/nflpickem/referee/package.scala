package com.nflpickem

import com.nflpickem.referee.db.RefereeMySqlConfig
import com.typesafe.config.ConfigFactory

/**
  * Created by jason on 1/31/17.
  */
package object referee {

  lazy val RefereeConfig = ConfigFactory.load().getConfig("nflpickem-referee")
  lazy val MySqlConfig = RefereeMySqlConfig(RefereeConfig.getConfig("mysql"))

}
