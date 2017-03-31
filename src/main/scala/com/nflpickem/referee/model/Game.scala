package com.nflpickem.referee.model

import com.nflpickem.referee.service.TeamService
import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 3/1/17.
  */
case class Game(id: Long, gameTime: DateTime, awayScore: Option[Int], awayTeam: Team, homeScore: Option[Int],
                homeTeam: Team, line: Float, offensiveYards: Option[Float], overUnder: Option[Float],
                gameType: GameType, seasonId: Long)

object Game {
  def fromDb(rs:WrappedResultSet): Game = {
    val id: Long = rs.long("id")
    val gameTime: DateTime = rs.jodaDateTime("game_time")
    val awayScore: Option[Int] = rs.intOpt("away_score")
    val awayTeam: Team = TeamService.getForAbbreviation(rs.string("away_team")).get
    val homeScore: Option[Int] = rs.intOpt("home_score")
    val homeTeam: Team = TeamService.getForAbbreviation(rs.string("home_team")).get
    val line: Float = rs.float("line")
    val offensiveYards: Option[Float] = rs.floatOpt("offensive_yards")
    val overUnder: Option[Float] = rs.floatOpt("over_under")
    val gameType: GameType = GameType(rs.string("game_type"))
    val seasonId: Long = rs.long("season_id")

    Game(id, gameTime, awayScore, awayTeam, homeScore, homeTeam, line, offensiveYards, overUnder, gameType, seasonId)
  }
}

sealed abstract class GameType(val id: String, val display: String)

object GameType {
  val allTypes: Set[GameType] = Set(RegularSeason(), AFCWildCard(), NFCWildCard(), AFCDivisional(), NFCDivisional(),
    ConferenceChampionship())
  val abbreviationMap: Map[String, GameType] = allTypes.map(g => (g.id, g)).toMap

  def apply(id: String): GameType = abbreviationMap(id)
}

case class RegularSeason() extends GameType("REG", "Regular Season")
case class AFCWildCard() extends GameType("AFC_WC", "AFC wild card")
case class NFCWildCard() extends GameType("NFC_WC", "NFC wild card")
case class AFCDivisional() extends GameType("AFC_DIV", "AFC divisional")
case class NFCDivisional() extends GameType("NFC_DIV", "NFC divisional")
case class ConferenceChampionship() extends GameType("CONF_CAMP", "Conference championship")