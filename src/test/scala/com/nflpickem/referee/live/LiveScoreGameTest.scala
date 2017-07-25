package com.nflpickem.referee.live

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by jason on 7/23/17.
  */
class LiveScoreGameTest extends FlatSpec with Matchers {

  "A LiveScoreGame" should "be final when final score is in" in {
    val json: Seq[String] = Seq("Sun","13:00:00","Final","","BAL","10","CIN","27","","","242","","REG17","2016")
    val lsg: LiveScoreGame = LiveScoreGame(json)

    lsg.isFinalScore shouldEqual true
  }

  it should "read game type" in {
    val json: Seq[String] = Seq("Sun","13:00:00","Final","","BAL","10","CIN","27","","","242","","REG17","2016")
    val lsg: LiveScoreGame = LiveScoreGame(json)

    lsg.gameType.id shouldEqual "REG"
  }

  it should "read week number" in {
    val json: Seq[String] = Seq("Sun","13:00:00","Final","","BAL","10","CIN","27","","","242","","REG17","2016")
    val lsg: LiveScoreGame = LiveScoreGame(json)

    lsg.weekNumber shouldBe 17
  }

}
