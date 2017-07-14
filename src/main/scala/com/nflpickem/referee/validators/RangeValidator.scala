package com.nflpickem.referee.validators

/**
  * Created by jason on 7/13/17.
  */
case class RangeValidator(lower: Int, upper: Int) extends Validator {

  override def validate(name: String, value: Int): Unit = {
    if (value < lower || value > upper)
      throw new IllegalArgumentException(s"$name must be between $lower and $upper")
  }

}
