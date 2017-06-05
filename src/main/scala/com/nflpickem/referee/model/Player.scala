package com.nflpickem.referee.model

import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 2/1/17.
  */
case class Player(id: Option[Long], firstName: String, lastName: String, email: String, defaultPick: String)
case class PlayerSignUp(id: Option[Long], leaguePass: String, firstName: String, lastName: String, email: String,
                        defaultPick: String, password: String)
case class RankedPlayer(id:Long, firstName:String, lastName:String, total:Int, rank:Int)

object Player {

  def apply(id: Option[Long], signUp: PlayerSignUp): Player =
    Player(id, signUp.firstName, signUp.lastName, signUp.email, signUp.defaultPick)

  def fromDb(rs: WrappedResultSet): Player = {
    val id = rs.long("id")
    val first = rs.string("first_name")
    val last = rs.string("last_name")
    val dPick = rs.string("default_pick")
    val email = rs.string("email")
    Player(Some(id), first, last, email, dPick)
  }

}

object RankedPlayer {

  def fromDb(rs:WrappedResultSet): RankedPlayer = {
    val id = rs.long("id")
    val rank = rs.int("rank")
    val first = rs.string("first_name")
    val last = rs.string("last_name")
    val total = rs.int("total")
    RankedPlayer(id, first, last, total, rank)
  }

}
