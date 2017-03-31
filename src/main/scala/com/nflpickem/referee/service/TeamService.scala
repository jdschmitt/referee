package com.nflpickem.referee.service

import com.nflpickem.referee.model.Team
import scalikejdbc._

/**
  * Created by jason on 3/3/17.
  */
object TeamService {

  def allTeams: Seq[Team] = DB.readOnly { implicit session =>
    sql"SELECT * FROM team".map(Team.fromDb).list().apply()
  }

  def getForAbbreviation(abbrev: String): Option[Team] = DB.readOnly { implicit seassion =>
    sql"SELECT * FROM team WHERE abbreviation = $abbrev".map(Team.fromDb).single().apply()
  }

}
