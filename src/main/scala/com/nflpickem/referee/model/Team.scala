package com.nflpickem.referee.model

import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 3/1/17.
  */
case class Team(id: Int, abbreviation: String, market: String, mascot: String)

object Team {

  def fromDb(rs: WrappedResultSet): Team =
    fromDb(rs, "id", "abbreviation", "market", "mascot")

  def fromDb(rs: WrappedResultSet, idCol: String, abbrevCol: String, marketCol: String, mascotCol: String): Team = {
    val id: Int = rs.int(idCol)
    val abbrev: String = rs.string(abbrevCol)
    val market: String = rs.string(marketCol)
    val mascot: String = rs.string(mascotCol)

    Team(id, abbrev, market, mascot)
  }

}
