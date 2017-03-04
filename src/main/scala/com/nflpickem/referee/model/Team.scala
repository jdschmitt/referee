package com.nflpickem.referee.model

/**
  * Created by jason on 3/1/17.
  */
sealed abstract class Team(val abbreviation: String, val market: String, val mascot: String)

object Team {
  val allTeams: Set[Team] = Set(ArizonaCardinals(), AtlantaFalcons(), DallasCowboys(), GreenBayPackers(), HoustonTexans())
  val abbreviationMap: Map[String, Team] = allTeams.map(t => (t.abbreviation, t)).toMap

  def fromAbbreviation(abbreviation: String): Team = abbreviationMap(abbreviation)
}

case class ArizonaCardinals() extends Team("ARI", "Arizona", "Cardinals")
case class AtlantaFalcons() extends Team("ATL", "Atlanta", "Falcons")
case class DallasCowboys() extends Team("DAL", "Dallas", "Cowboys")
case class GreenBayPackers() extends Team("GB", "Green Bay", "Packers")
case class HoustonTexans() extends Team("HOU", "Houston", "Texans")
case class PhiladelphiaEagles() extends Team("PHI", "Philadelphia", "Eagles")
case class NewYorkGiants() extends Team("NYG", "New York", "Giants")
case class NewYorkJets() extends Team("NYJ", "New York", "Jets")
case class NewEnglandPatriots() extends Team("NE", "New England", "Patriots")
case class DenverBroncos() extends Team("DEN", "Denver", "Broncos")
case class LARams() extends Team("LAM", "Los Angeles", "Rams")
case class LARaiders() extends Team("LAI", "Los Angeles", "Raiders")
case class SF49ers() extends Team("SF", "San Fransisco", "49ers")
case class WashingtionRedskins() extends Team("WAS", "Washington DC", "Redskins")
// TODO more teams to create...is this the right representation of all teams?