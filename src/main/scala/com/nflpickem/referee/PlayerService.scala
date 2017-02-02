package com.nflpickem.referee

import com.nflpickem.referee.model.Player
import scalikejdbc._

/**
  * Created by jason on 2/1/17.
  */
object PlayerService {

  def allUsers: Seq[Player] = DB.readOnly { implicit session =>
    sql"select * from player".map(Player.fromDb).list().apply()
  }

}
