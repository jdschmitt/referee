package com.nflpickem.referee.api

import scala.concurrent.{ExecutionContext, Future}
import spray.routing.{AuthenticationFailedRejection, RequestContext}
import spray.routing.authentication.{Authentication, ContextAuthenticator}

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
  *     val authenticator = RefereeTokenAuthenticator[User](
  *       headerName = "Auth-Token"
  *     ) { key =>
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

object RefereeTokenAuthenticator {

  object TokenExtraction {

    type TokenExtractor = RequestContext => Option[String]

    def fromHeader(headerName: String): TokenExtractor = { context: RequestContext =>
      context.request.headers.find(_.name == headerName).map(_.value)
    }

  }

  class TokenAuthenticator[T](extractor: TokenExtraction.TokenExtractor, authenticator: (String => Future[Option[T]]))
                             (implicit executionContext: ExecutionContext) extends ContextAuthenticator[T] {

    import AuthenticationFailedRejection._

    def apply(context: RequestContext): Future[Authentication[T]] =
      extractor(context) match {
        case None =>
          Future(
            Left(AuthenticationFailedRejection(CredentialsMissing, List()))
          )
        case Some(token) =>
          authenticator(token) map {
            case Some(t) =>
              Right(t)
            case None =>
              Left(AuthenticationFailedRejection(CredentialsRejected, List()))
          }
      }

  }

  def apply[T](headerName: String)(authenticator: (String => Future[Option[T]]))
              (implicit executionContext: ExecutionContext): TokenAuthenticator[T] = {

    def extractor(context: RequestContext) = TokenExtraction.fromHeader(headerName)(context)

    new TokenAuthenticator(extractor, authenticator)
  }

}