package http.responses

import scala.concurrent.duration._

import org.http4s.Response
import org.http4s.ResponseCookie
import org.http4s.SameSite

object syntax {

  implicit class CookieOps[F[_]](val fa: Response[F]) {
    // Max-Age=<number> Optional
//Indicates the number of seconds until the cookie expires. A zero or negative number will expire the cookie immediately. If both Expires and Max-Age are set, Max-Age has precedence.

    def createCookie(sessionId: String) = {
      val cookie = ResponseCookie(
        name = "SessionID" /* jwtCookie  */,
        content = sessionId /* jwt cookie name */,
        expires = None,
        maxAge = Some(1.hour.toSeconds),
        path = Some("/"),
        sameSite = Some(SameSite.Lax),
        secure = false,
        httpOnly = true,
        domain = Some(".localhost")
      )
      fa.addCookie(cookie)
    }

  }

}
