package com.nflpickem.referee.live

import com.nflpickem.referee.{Job, Whistle}
import com.nflpickem.referee.client.APIClient
import com.nflpickem.referee.model.Game
import com.nflpickem.referee.service.{GameService, LiveScoreWeekDao}
import com.nflpickem.referee.util.Converters._
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse

import scalaj.http.HttpResponse

/**
  * Created by jason on 7/16/17.
  */
class LiveScoresReader(apiClient: APIClient = APIClient.apply) extends Job with Whistle {

  implicit val formats = DefaultFormats

  val URL: String = "http://www.nfl.com/liveupdate/scorestrip/scorestrip.json"

  def readCurrentWeek: Option[LiveScoreWeek] = {
    val resp: HttpResponse[String] = apiClient.get(URL, Map())
    if (resp.code != 200)
      return None
    val cleanJson: String = Util.jsonCleaner(resp.body)
    Option(parse(cleanJson).extract[LiveScoreWeek])
  }

  def readCurrentWeekGames: Seq[LiveScoreGame] = readCurrentWeek.get.ss.map(LiveScoreGame(_))

  def updateGame(liveGame: LiveScoreGame, game: Game): Game = {
    val newGame: Game = game.copy(awayScore = liveGame.awayTeamScore, homeScore = liveGame.homeTeamScore)
    // Maybe gametime could change??
    GameService.updateGame(newGame)
    newGame
  }

  def createGame(lsg: LiveScoreGame): Game = {
    GameService.insertGame(Game(lsg))
  }

  override def run: Unit = execute

  def execute: Seq[LiveScoreGame] = {
    readCurrentWeekGames.map { liveGame: LiveScoreGame =>
      val lg: LiveScoreGame = processLiveGame(liveGame)
      LiveScoreWeekDao.upsert(lg)
    }
  }

  def processLiveGame(liveGame: LiveScoreGame): LiveScoreGame = {
    try {
      val g: Game = GameService.getGameFromLiveScoreGame(liveGame) match {
        case Some(game) => if (liveGame.isFinalScore) updateGame(liveGame, game) else game
        case None => createGame(liveGame)
      }
      liveGame.copy(gameId = g.id)
    } catch {
      case t: Throwable => {
        log.error("Failed to assign to game", t)
        liveGame
      }
    }
  }

}

object LiveScoresReader {
  def apply(apiClient: APIClient = APIClient.apply): LiveScoresReader = new LiveScoresReader(apiClient)
}