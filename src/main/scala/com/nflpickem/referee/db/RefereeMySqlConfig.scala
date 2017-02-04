package com.nflpickem.referee.db

import com.typesafe.config.Config

/**
  * Created by jason on 1/31/17.
  */
case class RefereeMySqlConfig(host:String, port:Int = 3306, database:String,
                              user:String, password:String, poolSize:Int) {

  val jdbcUrl = s"jdbc:mysql://$host:$port/$database"
}

object RefereeMySqlConfig {
  val DriverClass = "com.mysql.jdbc.Driver"

  private val DEFAULT_POOL_SIZE = 10

  def apply(config:Config):RefereeMySqlConfig = RefereeMySqlConfig(
    host = config.getString("host"),
    port = if(config.hasPath("port")) config.getInt("port") else 3306,
    database = config.getString("database"),
    user = config.getString("user"),
    password = config.getString("password"),
    poolSize = if(config.hasPath("pool-size")) config.getInt("pool-size") else DEFAULT_POOL_SIZE
  )
}