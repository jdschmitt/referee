package com.nflpickem.referee.model

import scalikejdbc.WrappedResultSet
import spray.json.DefaultJsonProtocol

/**
  * Created by jason on 2/1/17.
  */
case class Player(id:Long, firstName:String, lastName:String, username:String)

object Player {

  def fromDb(rs: WrappedResultSet): Player = {
    val id = rs.long("id")
    val first = rs.string("first_name")
    val last = rs.string("last_name")
    val username = rs.string("username")
    Player(id, first, last, username)
  }

}

object PlayerJsonProtocol extends DefaultJsonProtocol {
  implicit val playerFormat = jsonFormat4(Player.apply)
}
