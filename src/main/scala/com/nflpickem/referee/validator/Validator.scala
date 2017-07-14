package com.nflpickem.referee.validator

/**
  * Created by jason on 7/13/17.
  */
trait Validator {

  def validate(name: String, value: Int): Unit

}
