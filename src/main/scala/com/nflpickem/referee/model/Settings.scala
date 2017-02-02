package com.nflpickem.referee.model

import scalikejdbc.WrappedResultSet
import spray.json.DefaultJsonProtocol

/**
  * Created by jason on 2/1/17.
  */
case class Settings(commishNote:String, leaguePassword:String, secondPotStartWeek:Int, winnersPerWeek:Int)

object Settings {

  def fromDb(rs:WrappedResultSet): Settings = {
    val note = rs.string("commish_note")
    val pass = rs.string("league_password")
    val secondPotStart = rs.int("second_pot_start_week")
    val winnersPerWeek = rs.int("winners_per_week")
    Settings(note, pass, secondPotStart, winnersPerWeek)
  }

}

object SettingsJsonProtocol extends DefaultJsonProtocol {
  implicit val settingsFormat = jsonFormat4(Settings.apply)
}

