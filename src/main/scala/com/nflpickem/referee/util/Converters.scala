package com.nflpickem.referee.util

/**
  * Created by jason on 7/21/17.
  */
object Converters {

  implicit def toInt(str: String): Option[Int] = {
    str match {
      case "" => None
      case null => None
      case _ => try {
        Option(str.toInt)
      } catch {
        case _: NumberFormatException => None
      }
    }
  }

}
