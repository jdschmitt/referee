package com.nflpickem.referee.model

import org.joda.time.DateTime

/**
  * Created by jason on 3/1/17.
  */
case class Game(
                 id: Option[Long] = None,
                 version: Long = 1,
                 gameTime: DateTime,
                 awayScore: Option[Int] = None,
                 awayTeam: Team,
                 homeScore: Option[Int] = None,
                 homeTeam: Team,
                 line: Option[Float],
                 offensiveYards: Option[Float] = None,
                 overUnder: Option[Float] = None,
                 weekNumber: Int,
                 gameType: String,
                 seasonId: Long
               )

sealed abstract class GameType(val id: String, val display: String)

object GameType extends (String => GameType) {
  val allTypes: Set[GameType] = Set(PreSeason(), RegularSeason(), AFCWildCard(), NFCWildCard(), AFCDivisional(), NFCDivisional(),
    ConferenceChampionship())
  val abbreviationMap: Map[String, GameType] = allTypes.map(g => (g.id, g)).toMap

  def apply(id: String): GameType = abbreviationMap(id)
}

case class PreSeason() extends GameType("PRE", "Preseason")
case class RegularSeason() extends GameType("REG", "Regular Season")
case class AFCWildCard() extends GameType("AFC_WC", "AFC wild card")
case class NFCWildCard() extends GameType("NFC_WC", "NFC wild card")
case class AFCDivisional() extends GameType("AFC_DIV", "AFC divisional")
case class NFCDivisional() extends GameType("NFC_DIV", "NFC divisional")
case class ConferenceChampionship() extends GameType("CONF_CAMP", "Conference championship")