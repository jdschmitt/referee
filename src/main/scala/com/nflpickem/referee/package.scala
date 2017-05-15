package com.nflpickem

import com.nflpickem.referee.db.RefereeMySqlConfig
import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by jason on 1/31/17.
  */
package object referee {

  lazy val RefereeConfig: Config = ConfigFactory.load().getConfig("nflpickem-referee")
  lazy val RefereeEnv: String = sys.env("REFEREE_ENV")
  lazy val RefereeEnvConfig: Config = RefereeConfig.getConfig(s"$RefereeEnv")
  lazy val MySqlConfig = RefereeMySqlConfig(RefereeEnvConfig.getConfig("mysql"))

}
