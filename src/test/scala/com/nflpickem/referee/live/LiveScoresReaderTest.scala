package com.nflpickem.referee.live

import com.nflpickem.referee.client.APIClient
import org.scalatest.{FlatSpec, Matchers}

import scalaj.http.HttpResponse

/**
  * Created by jason on 7/16/17.
  */
class LiveScoresReaderTest extends FlatSpec with Matchers {

  class APIClientTest(body: String, code: Int) extends APIClient {
    override def get(url: String, params: Map[String, String]): HttpResponse[String] =
      HttpResponse(body, code, Map())
  }

  "A reader" should "read and parse the current week" in {
    val week: LiveScoreWeek = LiveScoresReader().readCurrentWeek.get
    week.ss.length should be > 0

    val game: Seq[String] = week.ss(0)
    game.length should be > 0
  }

  it should "read individual games" in {
    val games: Seq[LiveScoreGame] = LiveScoresReader().readCurrentWeekGames
    games.length should be > 0
    games.head.nflGameId should not be empty
  }

  ignore should "store a live game and create a game" in {
    val sampleFeed: String = "{\"ss\":[[\"Sun\",\"13:00:00\",\"Pregame\",\"\",\"BAL\",\"\",\"CIN\",\"\",\"\",\"\",242,\"\",\"REG1\",\"2017\"]]}"
    val reader: LiveScoresReader = LiveScoresReader(new APIClientTest(sampleFeed, 200))

    val lgs: Seq[LiveScoreGame] = reader.execute

    lgs.length shouldEqual 1

    val lg: LiveScoreGame = lgs.head

    lg.nflGameId shouldEqual 242
    lg.gameId shouldBe defined

  }

}
