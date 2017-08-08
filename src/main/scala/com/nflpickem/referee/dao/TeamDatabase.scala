package com.nflpickem.referee.dao

import com.nflpickem.referee.model.Team

/**
  * Created by jason on 8/5/17.
  */
trait TeamDatabase {

  def allTeams: Seq[Team]

  def getForAbbreviation(abbrev: String): Option[Team]

  def getById(id: Int): Option[Team]

}
