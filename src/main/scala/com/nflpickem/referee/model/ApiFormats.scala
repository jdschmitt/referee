package com.nflpickem.referee.model

import com.nflpickem.referee.api.AuthTokenResponse
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import spray.json.{DefaultJsonProtocol, RootJsonFormat, _}

/**
  * @version $Id$ $Rev: 223159 $ $Date: 2013-11-27 15:12:03 -0800 (Wed, 27 Nov 2013) $
  */
object ApiFormats extends DefaultJsonProtocol {

  implicit object JodaDateTimeFormat extends RootJsonFormat[DateTime] {

    val pattern = "MM-dd-YYYY HH:mm"
    val formatter: DateTimeFormatter = DateTimeFormat.forPattern(pattern)

    def write(obj: DateTime): JsValue = {
      JsString(formatter.print(obj))
    }

    def read(json: JsValue): DateTime = json match {
      case JsString(s) => try {
        formatter.parseDateTime(s)
      }
      catch {
        case _: Throwable => error(s)
      }
      case _ =>
        error(json.toString())
    }

    def error(v: Any): DateTime = {
      deserializationError(f"'$v' is not a valid date value. Dates must be '$pattern'")
    }
  }

//  implicit val gameTypeFormat: RootJsonFormat[GameType] = jsonFormat1(GameType.apply)
  implicit val tokenFormat: RootJsonFormat[Token] = jsonFormat1(Token.apply)
  implicit val teamFormat: RootJsonFormat[Team] = jsonFormat4(Team.apply)
  implicit val gameFormat: RootJsonFormat[Game] = jsonFormat13(Game.apply)
  implicit val playerFormat: RootJsonFormat[Player] = jsonFormat5(Player.apply)
  implicit val playerSignUpFormat: RootJsonFormat[PlayerSignUp] = jsonFormat7(PlayerSignUp.apply)
  implicit val rankedPlayerFormat: RootJsonFormat[RankedPlayer] = jsonFormat5(RankedPlayer.apply)
  implicit val roleFormat: RootJsonFormat[Role] = jsonFormat3(Role.apply)
  implicit val seasonFormat: RootJsonFormat[Season] = jsonFormat4(Season.apply)
  implicit val authTokenFormat: RootJsonFormat[AuthTokenResponse] = jsonFormat1(AuthTokenResponse.apply)
  implicit val playerRoleFormat: RootJsonFormat[PlayerRole] = jsonFormat2(PlayerRole.apply)
  implicit val pickFormat: RootJsonFormat[Pick] = jsonFormat8(Pick.apply)
}