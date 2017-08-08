package com.nflpickem.referee.dao

import com.nflpickem.referee.live.LiveScoreGame

/**
  * Created by jason on 8/7/17.
  */
trait LiveScoreWeekDatabase {

  def insert(lg: LiveScoreGame): Boolean

  def update(lg: LiveScoreGame): Boolean

  def upsert(lg:LiveScoreGame): LiveScoreGame

  def get(id: String): Option[LiveScoreGame]

}
