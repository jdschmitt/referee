package com.nflpickem.referee.model

import scalikejdbc.WrappedResultSet
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
  * Created by jason on 3/1/17.
  */
case class Team(id: Long, abbreviation: String, market: String, mascot: String)

object Team {

  def fromDb(rs: WrappedResultSet): Team = {
    val id: Long = rs.long("id")
    val abbrev: String = rs.string("abbreviation")
    val market: String = rs.string("market")
    val mascot: String = rs.string("mascot")

    Team(id, abbrev, market, mascot)
  }

}


object TeamJsonProtocol extends DefaultJsonProtocol {
  implicit val teamFormat: RootJsonFormat[Team] = jsonFormat4(Team.apply)
}
