package modules

import cats.effect.kernel.Async
import org.http4s.server.AuthMiddleware
import cats.effect._
import domain._
import middleware.CookieAuthenticationMiddleware
import org.http4s._
import org.http4s.server.middleware._
import scala.concurrent.duration._
import http.routes._
import org.http4s.Method._
import org.typelevel.ci._
import cats.syntax.all._

import cats.effect.syntax.all._
import cats.data.Kleisli

sealed abstract class HttpApi[F[_]: Async] private (
    services: Services[F]
) {

  private val userAuthMiddleware: AuthMiddleware[F, User] =
    CookieAuthenticationMiddleware[F, User](services.redis)

  private val adminAuthMiddleware: AuthMiddleware[F, User] =
    CookieAuthenticationMiddleware[F, User](services.redis)

// val redis = CatsRedisServiceLive(CatsRedisServiceLive.resource)

//  val userAuthMiddleware: AuthMiddleware[IO, User] =
//    CookieAuthenticationMiddleware[IO, User](redis)

//  val route: HttpRoutes[IO] = routes(userAuthMiddleware)

// Auth routes
// private val loginRoutes: HttpRoutes[F]  = LoginRoutes[F](security.auth).routes
// private val logoutRoutes: HttpRoutes[F] = LogoutRoutes[F](security.auth).routes(usersMiddleware)
// private val userRoutes: HttpRoutes[F]   = UserRoutes[F](security.auth).routes

// Open routes
  private val healthRoutes = HealthRoutes[F]().routes
// private val brandRoutes    = BrandRoutes[F](services.brands).routes
// private val categoryRoutes = CategoryRoutes[F](services.categories).routes
// private val itemRoutes     = ItemRoutes[F](services.items).routes

  // Secured routes
// private val cartRoutes     = CartRoutes[F](services.cart).routes(usersMiddleware)
// private val checkoutRoutes = CheckoutRoutes[F](programs.checkout).routes(usersMiddleware)
// private val orderRoutes    = OrderRoutes[F](services.orders).routes(usersMiddleware)

  // Admin routes
// private val adminBrandRoutes    = AdminBrandRoutes[F](services.brands).routes(adminMiddleware)
// private val adminCategoryRoutes = AdminCategoryRoutes[F](services.categories).routes(adminMiddleware)
// private val adminItemRoutes     = AdminItemRoutes[F](services.items).routes(adminMiddleware)

  // Combining all the http routes//maybe userRoutes?
// private val openRoutes: HttpRoutes[F] =
//   healthRoutes <+> itemRoutes <+> brandRoutes <+>
//     categoryRoutes <+> loginRoutes <+> userRoutes <+>
//     logoutRoutes <+> cartRoutes <+> orderRoutes <+>
//     checkoutRoutes

// private val adminRoutes: HttpRoutes[F] =
//   adminItemRoutes <+> adminBrandRoutes <+> adminCategoryRoutes

// private val routes: HttpRoutes[F] = Router(
//   version.v1            -> openRoutes,
//   version.v1 + "/admin" -> adminRoutes
// )
  val headerName = "X-Csrf-Token" // default
  val cookieName = "csrf-token" // default

  private val corsService = CORS.policy
    .withAllowOriginHost(Set("http://localhost:3000"))
    .withAllowMethodsIn(Set(POST, PUT, GET, DELETE))
    .withAllowCredentials(
      false
    ) // set to true for csrf// The default behavior of cross-origin resource requests is for
    // requests to be passed without credentials like cookies and the Authorization header
    // Cookies are not set on cross-origin requests (CORS) by default. To enable cookies on an API, you will set Access-Control-Allow-Credentials=true.
    // The browser will reject any response that includes Access-Control-Allow-Origin=*
    .withAllowHeadersIn(Set(ci"X-Csrf-Token", ci"Content-Type"))

  private def csrfService = CSRF
    .withGeneratedKey[F, F](request =>
      CSRF.defaultOriginCheck(request, "localhost", Uri.Scheme.http, None)
    )
    .map(builder =>
      builder
        // .withCookieName(cookieName)
        .withCookieDomain(Some("localhost"))
        .withCookiePath(Some("/"))
        .withCookieSecure(true) // defaults to false
        .withCookieHttpOnly(
          true
        ) // The CSRF token cookie must not have httpOnly flag,
        // defaults to true
        .withCookieName(
          "__HOST-CSRF-TOKEN"
        ) // sent only to this host, no subdomains
        .build
        .validate()
    )
    .toResource

  private val middleware: HttpRoutes[F] => HttpRoutes[F] = {
    { http: HttpRoutes[F] =>
      AutoSlash(http)
    } andThen { http: HttpRoutes[F] =>
      corsService(http)
    } andThen { http: HttpRoutes[F] =>
      Timeout(60.seconds)(http)
    }
  }

  private val loggers: HttpApp[F] => HttpApp[F] = {
    { http: HttpApp[F] =>
      RequestLogger.httpApp(true, true)(http)
    } andThen { http: HttpApp[F] =>
      ResponseLogger.httpApp(true, true)(http)
    }
  }
  val routes: HttpRoutes[F] = ???
  val corsHtppApp: HttpApp[F] = loggers(middleware(routes).orNotFound)
  val crsfHttpApp: Resource[F, HttpApp[F]] = csrfService.map(_(corsHtppApp))
}

object HttpApi {
  def make[F[_]: Async](
      services: Services[F]
  ): HttpApi[F] =
    new HttpApi[F](services) {}
}
