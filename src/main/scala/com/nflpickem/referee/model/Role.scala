package com.nflpickem.referee.model

import scalikejdbc.WrappedResultSet

/**
  * Created by jason on 5/14/17.
  */
case class Role(id: Option[Long], version: Long, authority: String)

object Role {
  def fromDb(rs:WrappedResultSet): Role = {
    val id: Long = rs.long("id")
    val version: Long = rs.long("version")
    val authority: String = rs.string("authority")
    Role(Some(id), version, authority)
  }
}
