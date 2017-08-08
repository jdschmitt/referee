package com.nflpickem.referee.live

import com.google.inject.testing.fieldbinder.{Bind, BoundFieldModule}
import com.google.inject.{Guice, Inject}
import com.nflpickem.referee.client.APIClient
import com.nflpickem.referee.dao.{GameDatabase, LiveScoreWeekDatabase, SeasonDatabase, TeamDatabase}
import com.nflpickem.referee.model.{Game, Season, Team}
import com.nflpickem.referee.service.GameService
import com.nflpickem.referee.util.{ClockService, TestRefereeClock, WeekHelper}
import org.joda.time.DateTime
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scalaj.http.HttpResponse

/**
  * Created by jason on 7/16/17.
  */
class LiveScoresReaderTest extends FlatSpec with Matchers with BeforeAndAfter with MockitoSugar {

  val sampleFeed: String = "{\"ss\":[[\"Sun\",\"13:00:00\",\"Pregame\",\"\",\"BAL\",\"\",\"CIN\",\"\",\"\",\"\",242,\"\",\"REG1\",\"2017\"]]}"
  @Bind val apiClient: APIClient = new APIClientTest(sampleFeed, 200)

  @Bind val teamDatabase: TeamDatabase = mock[TeamDatabase]
  @Bind val seasonDatabase: SeasonDatabase = mock[SeasonDatabase]
  @Bind val gameDatabase: GameDatabase = mock[GameDatabase]
  @Bind val liveScoreWeekDatabase: LiveScoreWeekDatabase = mock[LiveScoreWeekDatabase]
  @Bind val clock: ClockService = TestRefereeClock

  @Inject var WeekHelper: WeekHelper = _
  @Inject var gameService: GameService = _
  @Inject var reader: LiveScoresReader = _

  class APIClientTest(body: String, code: Int) extends APIClient {
    override def get(url: String, params: Map[String, String]): HttpResponse[String] =
      HttpResponse(body, code, Map())
  }

  before {
    Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this)
  }

  "A reader" should "read and parse the current week" in {
    val week: LiveScoreWeek = reader.readCurrentWeek.get
    week.ss.length should be > 0

    val game: Seq[String] = week.ss.head
    game.length should be > 0
  }

  it should "read individual games" in {
    val games: Seq[LiveScoreGame] = reader.readCurrentWeekGames
    games.length should be > 0
    games.head.nflGameId should not be empty
  }

  it should "be convertable to a Game" in {
    val games: Seq[LiveScoreGame] = reader.readCurrentWeekGames
    games.length should be > 0

    when(teamDatabase.getForAbbreviation("BAL")).thenReturn(Option(Team(1, "BAL", "Baltimore", "Ravens")))
    when(teamDatabase.getForAbbreviation("CIN")).thenReturn(Option(Team(1, "CIN", "Cinncinnati", "Bengals")))
    when(seasonDatabase.getSeasonByYear(any[Int])).thenReturn(Option(Season(Some(1), DateTime.now, DateTime.now, DateTime.now)))

    val game: Game = gameService.gameFromLiveGame(games.head)
    game.homeTeam should not be null
  }

  ignore should "store a live game and create a game" in {

    when(gameDatabase.getGameFromLiveScoreGame(any[LiveScoreGame])).thenReturn(None)
    val mockGame: Game = mock[Game]
    when(gameDatabase.insertGame(any[Game])).thenReturn(mockGame)
    when(liveScoreWeekDatabase.upsert(any[LiveScoreGame])).thenReturn(mock[LiveScoreGame])

    val lgs: Seq[LiveScoreGame] = reader.execute

    lgs.length shouldEqual 1

    val lg: LiveScoreGame = lgs.head

    lg.nflGameId shouldEqual 242
    lg.gameId shouldBe defined

  }

}
