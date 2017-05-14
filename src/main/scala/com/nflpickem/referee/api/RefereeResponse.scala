package com.nflpickem.referee.api

import spray.http.StatusCode
import spray.http.StatusCodes._
import spray.json._

/**
  * Created by jason on 4/30/17.
  */
trait RefereeResponse {

  val statusCode:StatusCode
  val exception:Option[Throwable]
  val errorMessage:Option[String]

  val offset:Option[Int] = None
  val limit:Option[Int] = None
  val total:Option[Long] = None

  val dataJson: JsValue

  lazy val meta: JsObject = {
    val c = Vector("code" -> JsNumber(statusCode.intValue))

    //if there's an explicit error message, use that,
    // else fall back to the exception's message
    val errorMessageOpt:Option[JsString] =
    errorMessage.map(JsString(_)).orElse {
      exception.map { t =>
        JsString(t.getMessage)
      }
    }

    val e = errorMessageOpt.map(m => Vector("error" -> m)).getOrElse(Vector.empty)
    val o = if(offset.isDefined) Vector("offset" -> JsNumber(offset.get)) else Vector.empty
    val l = if(limit.isDefined) Vector("limit" -> JsNumber(limit.get)) else Vector.empty
    val t = if(total.isDefined) Vector("total" -> JsNumber(total.get)) else Vector.empty

    JsObject((c ++ e ++ o ++ l ++ t).toMap)
  }

}

case class RefereeErrorResponse(statusCode:StatusCode, exception:Option[Throwable], errorMessage:Option[String] = None)
  extends RefereeResponse {
  val dataJson: JsArray = JsArray.empty
}

object RefereeErrorResponse {
  def apply(statusCode:StatusCode, errorMessage:String): RefereeErrorResponse =
    RefereeErrorResponse(statusCode, None, Some(errorMessage))

  def apply(statusCode:StatusCode, error:Throwable): RefereeErrorResponse =
    RefereeErrorResponse(statusCode, Some(error), None)
}

/**
  * Returns a JSON response consisting of a single object in the 'data'
  * field.
  */
case class RefereeObjectResponse[T : JsonWriter](data:T, statusCode:StatusCode = OK)
  extends RefereeResponse {
  lazy val dataJson: JsValue = data.toJson
  val exception = None
  val errorMessage = None
}

object RefereeObjectResponse extends DefaultJsonProtocol {
  implicit val refereeJsonFormat = new JsonWriter[RefereeResponse] {
    def write(o:RefereeResponse): JsObject = {
      val meta = Seq("meta" -> o.meta.asInstanceOf[JsValue])
      val data = o.dataJson match {
        case o:JsObject => Seq("data" -> o)
        case a:JsArray => Seq("data" -> a)
        case _ => Seq.empty
      }

      JsObject((meta ++ data).toMap)
    }
  }

  implicit val refereeErrorFormat:JsonWriter[RefereeErrorResponse] =
    refereeJsonFormat.asInstanceOf[JsonWriter[RefereeErrorResponse]]
}