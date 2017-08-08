package com.nflpickem.referee.dao

import com.nflpickem.referee.model.Season

/**
  * Created by jason on 2/23/17.
  */
trait SeasonDatabase {

  def getSeasons: Seq[Season]

  def insertSeason(season: Season): Season

  def getSeason(id: Long): Option[Season]

  def getSeasonByYear(year: Int): Option[Season]

  def deleteSeason(id: Long): Boolean

  def currentSeason: Option[Season]

  def currentSeasonId(seasonIdOpt: Option[Long]): Long

}