package com.nflpickem.referee

import com.nflpickem.referee.model.Settings
import scalikejdbc._

/**
  * Created by jason on 2/1/17.
  */
object SettingsService {

  def get: Settings = DB.readOnly { implicit session =>
    sql"select * from settings".map(Settings.fromDb).list().apply().head
  }

}
