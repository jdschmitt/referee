package com.nflpickem.referee.model

import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 2/19/17.
  */
case class Season(id: Option[Long], firstRegularGameDate: DateTime, firstPlayoffGameDate: DateTime, superBowlDate: DateTime)

object Season {

  def fromDb(rs: WrappedResultSet): Season = {
    val id = rs.long("id")
    val firstRegGameDate = rs.jodaDateTime("first_reg_game_date")
    val firstPlayoffGameDate = rs.jodaDateTime("first_playoff_game_date")
    val superBowlDate = rs.jodaDateTime("super_bowl_date")
    Season(Option(id), firstRegGameDate, firstPlayoffGameDate, superBowlDate)
  }

}
