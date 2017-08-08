package com.nflpickem.referee

import com.google.inject.AbstractModule
import com.nflpickem.referee.client.APIClient
import com.nflpickem.referee.dao._
import com.nflpickem.referee.util.{ClockService, RefereeClock}
import net.codingwell.scalaguice.ScalaModule

/**
  * Created by jason on 8/7/17.
  */
class RefereeModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bindDao()
    bind[APIClient].toInstance(APIClient.apply)
    bind[ClockService].toInstance(RefereeClock)
  }

  def bindDao(): Unit = {
    bind[TeamDatabase].toInstance(TeamDAO)
    bind[GameDatabase].to[GameDAO]
    bind[SeasonDatabase].toInstance(SeasonDAO)
    bind[LiveScoreWeekDatabase].toInstance(LiveScoreWeekDAO)
  }

}
