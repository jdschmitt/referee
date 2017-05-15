package com.nflpickem.referee.api

import com.nflpickem.referee.Whistle
import com.nflpickem.referee.model.{ApiFormats, PlayerSignUp}
import spray.routing.authentication.{Authentication, ContextAuthenticator}
import spray.routing.{AuthenticationFailedRejection, RequestContext}
import spray.json._

import scala.concurrent.{ExecutionContext, Future}

/** Token based authentication for Spray Routing.
  *
  * Extracts an API key from the header or query string and authenticates requests.
  *
  * TokenAuthenticator[T] takes arguments for the named header/query string containing the API key and
  * an authenticator that returns an Option[T]. If None is returned from the authenticator, the request
  * is rejected.
  *
  * Usage:
  *
  *     val authenticator = LeaguePasswordAuthenticator[User] { key =>
  *       User.findByAPIKey(key)
  *     }
  *
  *     def auth: Directive1[User] = authenticate(authenticator)
  *
  *     val home = path("home") {
  *       auth { user =>
  *         get {
  *           complete("OK")
  *         }
  *       }
  *     }
  */

object LeaguePasswordAuthenticator extends Whistle {

  object SignUpPasswordExtraction {

    type SignUpPassword = String
    type SignUpPasswordExtractor = RequestContext => Option[SignUpPassword]

    import ApiFormats._

    def extractPassword: SignUpPasswordExtractor = { ctx: RequestContext =>
      try {
        val playerSignUp: PlayerSignUp = playerSignUpFormat.read(ctx.request.entity.asString.parseJson)
        Some(playerSignUp.leaguePass)
      } catch {
        case t: Throwable => log.warn(s"Sign up attempt failed: ${t.getMessage}")
          None
      }
    }

  }

  class SignUpPasswordAuthenticator[T](
                                        extractor: SignUpPasswordExtraction.SignUpPasswordExtractor,
                                        authenticator: (String => Future[Option[T]])
                                      )
                                      (implicit executionContext: ExecutionContext) extends ContextAuthenticator[T] {

    import AuthenticationFailedRejection._

    def apply(context: RequestContext): Future[Authentication[T]] =
      extractor(context) match {
        case None =>
          Future(
            Left(AuthenticationFailedRejection(CredentialsMissing, List()))
          )
        case Some(signUpPassword) =>
          authenticator(signUpPassword) map {
            case Some(t) =>
              Right(t)
            case None =>
              Left(AuthenticationFailedRejection(CredentialsRejected, List()))
          }
      }

  }

  def apply[T](authenticator: (String => Future[Option[T]]))
              (implicit executionContext: ExecutionContext): SignUpPasswordAuthenticator[T] = {

    def extractor(context: RequestContext) = SignUpPasswordExtraction.extractPassword(context)

    new SignUpPasswordAuthenticator(extractor, authenticator)
  }

}