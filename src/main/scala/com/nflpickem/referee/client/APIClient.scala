package com.nflpickem.referee.client

import scalaj.http.{Http, HttpResponse}

/**
  * Created by jason on 7/16/17.
  */
object APIClient {

  private val headers = Map[String,String](
    ("Content-Type", "application/json"),
    ("Accept", "application/json"))

  private val apiReadTimeout = 3000

  def get(url: String, params: Map[String, String]): HttpResponse[String] = {
    Http(url)
      .headers(headers)
      .params(params)
      .timeout(connTimeoutMs = 1000, readTimeoutMs = apiReadTimeout)
      .asString
  }

}
