package service

import akka.actor.{ ActorLogging, ActorRef, Props }
import akka.io.IO
import scala.concurrent.duration.FiniteDuration
import spray.can.Http
import spray.http.MediaTypes._
import spray.httpx.SprayJsonSupport
import spray.routing.{HttpService, HttpServiceActor, Route}
import spray.json.DefaultJsonProtocol


case class Message(message: String)

object MessageJsonProtocol extends DefaultJsonProtocol {
  implicit val format = jsonFormat1(Message)
}

/**
 * Spray service
 *   - a REST under `spray-json-message/`
 *   - a HTML under `spray-html/`
 */
trait SprayService extends HttpService {

  import MessageJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  def adRoute : Route =
    path( "spray-json-message" ) {
      get {
        complete {
          Message("Hello mama!")
        }
      }
    } ~
    path("spray-html") {
      get {
        respondWithMediaType(`text/html`) {
          complete {
            <html>
              <body>
                <h1>Hello papa!</h1>
              </body>
            </html>
          }
        }
      }
    }
}
