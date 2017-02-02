package com.nflpickem.referee.db

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import scalikejdbc.{ConnectionPool, DataSourceConnectionPool}

/**
  * Created by jason on 1/31/17.
  */
object RefereeMySqlConnectionPool {
  private var mysqlConfig: Option[RefereeMySqlConfig] = None

  lazy val datasource = {
    mysqlConfig match {
      case Some(config) => {
        val hikariConfig = new HikariConfig()
        Class.forName(RefereeMySqlConfig.DriverClass)
        hikariConfig.setJdbcUrl(config.jdbcUrl)
        hikariConfig.setUsername(config.user)
        hikariConfig.setPassword(config.password)
        hikariConfig.setMaximumPoolSize(config.poolSize)
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true")
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
//        hikariConfig.setMetricRegistry(co.zing.k2.MetricsRegistry)
        hikariConfig.setPoolName("default-mysql-pool")
        new HikariDataSource(hikariConfig)
      }

      case None =>
        throw new IllegalStateException("Attempted to start datasource without MySQL configuration set, please call startup()")
    }
  }

  /**
    * Must be called before any actors are created.
    *
    * @param config
    */
  def startup(config: RefereeMySqlConfig): Unit = this.synchronized {
    mysqlConfig = Option(config)
    ConnectionPool.singleton(new DataSourceConnectionPool(RefereeMySqlConnectionPool.datasource))
  }

  def shutdown(): Unit = this.synchronized {
    mysqlConfig = None
    datasource.close()
  }
}
