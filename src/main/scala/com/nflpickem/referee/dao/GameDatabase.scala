package com.nflpickem.referee.dao

import com.nflpickem.referee.live.LiveScoreGame
import com.nflpickem.referee.model.Game

/**
  * Created by jason on 3/3/17.
  */
trait GameDatabase {

  def insertGame(game: Game): Game

  def updateGame(game: Game): Boolean

  def getGamesForWeek(week: Int): Seq[Game]

  def deleteGame(id: Long): Boolean

  def getGame(id: Long): Option[Game]

  def getGamesForSeason(seasonId: Long): Seq[Game]

  def getGameFromLiveScoreGame(lsg: LiveScoreGame): Option[Game]

}
