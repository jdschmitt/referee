package com.nflpickem.referee.db

import com.typesafe.config.Config

/**
  * Created by jason on 1/31/17.
  */
// NOTE: No port used here...didn't seem to be necessary either locally or heroku (where port was actually a problem)
case class RefereeMySqlConfig(host:String, database:String,
                              user:String, password:String, poolSize:Int) {

  val jdbcUrl = s"jdbc:mysql://$host/$database?autoReconnect=true&useSSL=false"
}

object RefereeMySqlConfig {
  val DriverClass = "com.mysql.jdbc.Driver"

  private val DEFAULT_POOL_SIZE = 10

  def apply(config:Config):RefereeMySqlConfig = RefereeMySqlConfig(
    host = config.getString("host"),
    database = config.getString("database"),
    user = config.getString("user"),
    password = config.getString("password"),
    poolSize = if(config.hasPath("pool-size")) config.getInt("pool-size") else DEFAULT_POOL_SIZE
  )
}