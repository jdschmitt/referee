package com.nflpickem.referee.validators

/**
  * Created by jason on 7/13/17.
  */
trait Validator {

  def validate(name: String, value: Int): Unit

}
