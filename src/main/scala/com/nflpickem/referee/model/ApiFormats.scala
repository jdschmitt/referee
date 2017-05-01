package com.nflpickem.referee.model

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import spray.json.{DefaultJsonProtocol, RootJsonFormat, _}

/**
  * @version $Id$ $Rev: 223159 $ $Date: 2013-11-27 15:12:03 -0800 (Wed, 27 Nov 2013) $
  */
object ApiFormats extends DefaultJsonProtocol {

  implicit object JodaDateTimeFormat extends RootJsonFormat[DateTime] {

//    val formatter = ISODateTimeFormat.basicDateTimeNoMillis
    val formatter = DateTimeFormat.forPattern("MM-DD-YYYY hh:mm")

    def write(obj: DateTime): JsValue = {
      JsString(formatter.print(obj))
    }

    def read(json: JsValue): DateTime = json match {
      case JsString(s) => try {
        formatter.parseDateTime(s)
      }
      catch {
        case t: Throwable => error(s)
      }
      case _ =>
        error(json.toString())
    }

    def error(v: Any): DateTime = {
      val example = formatter.print(0)
      deserializationError(f"'$v' is not a valid date value. Dates must be in compact ISO-8601 format, e.g. '$example'")
    }
  }

  implicit val teamFormat: RootJsonFormat[Team] = jsonFormat4(Team.apply)
//  implicit val gameTypeFormat: RootJsonFormat[GameType] = jsonFormat1(GameType.apply)
  implicit val gameFormat: RootJsonFormat[Game] = jsonFormat13(Game.apply)
}