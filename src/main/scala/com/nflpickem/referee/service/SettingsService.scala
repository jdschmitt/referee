package com.nflpickem.referee.service

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Settings
import scalikejdbc._

/**
  * Created by jason on 2/1/17.
  */
object SettingsService extends Whistle {

  def getSettings: Option[Settings] = DB.readOnly { implicit session =>
    sql"select * from settings".map(Settings.fromDb).single().apply()
  }

}
