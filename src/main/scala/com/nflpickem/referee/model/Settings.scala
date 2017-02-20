package com.nflpickem.referee.model

import com.nflpickem.referee.CurrentWeek
import scalikejdbc.WrappedResultSet
import spray.json.DefaultJsonProtocol

/**
  * Created by jason on 2/1/17.
  */
case class Settings(commishNote:String, leaguePassword:String, secondPotStartWeek:Int, winnersPerWeek:Int, currentSeasonId:Long)

object Settings {

  def fromDb(rs:WrappedResultSet): Settings = {
    val note = rs.string("commish_note")
    val pass = rs.string("league_password")
    val secondPotStart = rs.int("second_pot_start_week")
    val winnersPerWeek = rs.int("winners_per_week")
    val currentSeasonId = rs.long("current_season_id")
    Settings(note, pass, secondPotStart, winnersPerWeek, currentSeasonId)
  }

}

object SettingsJsonProtocol extends DefaultJsonProtocol {
  implicit val settingsFormat = jsonFormat5(Settings.apply)
  implicit val currentWeekFormat = jsonFormat1(CurrentWeek.apply)
}
