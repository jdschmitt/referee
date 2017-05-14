package com.nflpickem.referee.service

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.Role
import scalikejdbc._

/**
  * Created by jason on 2/1/17.
  */
object AuthService extends Whistle {

  def addRole(role: Role): Role = DB.autoCommit { implicit session =>
    val stmt = sql"INSERT INTO role (version, authority) VALUES (${role.version}, ${role.authority});"

    try {
      val id: Long = stmt.updateAndReturnGeneratedKey.apply()

      role.copy(id = Some(id))

    } catch {
      case t: Throwable => log.error("Error while inserting new role", t)
        println(s"error while inserting new role: ${t.getMessage}")
        role
    }
  }

  def getRoles: Seq[Role] = DB.readOnly { implicit session =>
    sql"SELECT * FROM role;".map(Role.fromDb).list().apply()
  }

}
