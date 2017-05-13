package com.nflpickem.referee.model

import com.nflpickem.referee.service.CurrentWeek
import scalikejdbc.WrappedResultSet
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
  * Created by jason on 2/1/17.
  */
case class Settings(commishNote:String, leaguePassword:String, secondPotStartWeek:Int, winnersPerWeek:Int, seasonId:Long)

object Settings {

  def fromDb(rs:WrappedResultSet): Settings = {
    val note = rs.string("commish_note")
    val pass = rs.string("league_password")
    val secondPotStart = rs.int("second_pot_start_week")
    val winnersPerWeek = rs.int("winners_per_week")
    val seasonId = rs.long("season_id")
    Settings(note, pass, secondPotStart, winnersPerWeek, seasonId)
  }

}

object SettingsJsonProtocol extends DefaultJsonProtocol {
  implicit val settingsFormat: RootJsonFormat[Settings] = jsonFormat5(Settings.apply)
  implicit val currentWeekFormat: RootJsonFormat[CurrentWeek] = jsonFormat1(CurrentWeek.apply)
}
