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
import http.controllers.SwaggerDocs
import http.routes.secured._
import services._
import  http.routes.version
import org.http4s.server._
sealed abstract class HttpApi[F[_]: Async] private (
    services: Services[F]
) {

  private val userAuthMiddleware: AuthMiddleware[F, User] =
    CookieAuthenticationMiddleware[F, User](services.redis)

  private val adminAuthMiddleware: AuthMiddleware[F, User] =
    CookieAuthenticationMiddleware[F, User](services.redis)

// Auth routes
// private val loginRoutes: HttpRoutes[F]  = LoginRoutes[F](security.auth).routes
// private val logoutRoutes: HttpRoutes[F] = LogoutRoutes[F](security.auth).routes(usersMiddleware)
// private val userRoutes: HttpRoutes[F]   = UserRoutes[F](security.auth).routes

// Open routes
  private val healthRoutes = HealthRoutes[F]().routes

  val swaggerRoute = SwaggerDocs.swaggerRoute
// private val brandRoutes    = BrandRoutes[F](services.brands).routes
// private val categoryRoutes = CategoryRoutes[F](services.categories).routes
// private val itemRoutes     = ItemRoutes[F](services.items).routes

  // Secured routes

private val durationRoutes=  DurationRoutes( new DurationService[F]{}).routes(userAuthMiddleware)
private val epicRoutes= EpicRoutes(new EpicService[F] {}).routes(userAuthMiddleware)

private  val issueRoutes= IssueRoutes( new IssueService[F] {}).routes(userAuthMiddleware)

private  val issueTypeRoutes= IssueTypeRoutes( new IssueTypeService[F] {}).routes(userAuthMiddleware)

private val priorityRoutes= PriorityRoutes( new PriorityService[F] {}).routes(userAuthMiddleware)

private val projectRoutes= ProjectRoutes( new ProjectService[F] {}).routes(userAuthMiddleware)

private val sprintIssueRoutes= SprintIssueRoutes( new SprintIssueService[F] {}).routes(userAuthMiddleware)
private val sprintRoutes= SprintRoutes(new SprintService[F] {}).routes(userAuthMiddleware)


private val statusRoutes= StatusRoutes( new StatusService[F] {}).routes(userAuthMiddleware)

private val systemUserRoutes= SystemUserRoutes(new SystemUserService[F] {},new EmailService[F] {}).routes(userAuthMiddleware)

private val teamRoutes= TeamRoutes(new TeamService[F] {}).routes(userAuthMiddleware)

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


private val openRoutes: HttpRoutes[F] =
  healthRoutes <+>epicRoutes <+>issueRoutes <+>issueTypeRoutes<+>priorityRoutes<+>projectRoutes<+>sprintIssueRoutes<+>sprintRoutes<+>statusRoutes<+>systemUserRoutes<+>teamRoutes

 private val routes: HttpRoutes[F] = Router(
  version.v1            -> openRoutes,
//   version.v1 + "/admin" -> adminRoutes
 )
  private val headerName = "X-Csrf-Token" // default
  private val cookieName = "csrf-token" // default

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

  // curl -v XPOST http://localhost:8080/user -H "Origin:http//localhost" -H "X-Csrf-Token:2072583F48A6F7C9A27A9F0441DA012522D9C4D038B2FA3EA7F72376CE473E9C-1709191392115-EA132FDB69B957D9A1FCB0C7375328300E6868BC"  --cookie "csrf-token:2072583F48A6F7C9A27A9F0441DA012522D9C4D038B2FA3EA7F72376CE473E9C-1709191392115-EA132FDB69B957D9A1FCB0C7375328300E6868BC"
  // curl -v  http://localhost:8080/healthcheck -H "__HOST-CSRF-TOKEN:" -H "csrf-token:"
  //
  def csrfService: Resource[F, Middleware[F, Request[F], Response[F], Request[
    F
  ], Response[F]]] = CSRF
    .withGeneratedKey[F, F](request =>
      CSRF.defaultOriginCheck(request, "localhost", Uri.Scheme.http, None)
    )
    .map(builder =>
      builder
        // .withCookieName(cookieName)

        // .withCookieDomain(Some("localhost"))
        .withCookiePath(Some("/"))
        .withCookieSecure(false)
        // defaults to false
        .withCookieHttpOnly(
          false
        )
        // The CSRF token cookie must not have httpOnly flag,
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
        .build // the length of this cookie is 119
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

  private val loggers: HttpRoutes[F] => HttpRoutes[F] = {
    { httpRoutes: HttpRoutes[F] =>
      RequestLogger.httpRoutes(true, true, _ => false)(httpRoutes)
    } andThen { httpRoutes: HttpRoutes[F] =>
      ResponseLogger.httpRoutes(true, true, _ => false)(httpRoutes)
    }
  }

  val corsHtppRoutes: HttpRoutes[F] = loggers(middleware(routes))
  val crsfHttpApp: Resource[F, HttpApp[F]] =
    csrfService.map(_(corsHtppRoutes.orNotFound))
}

object HttpApi {
  def make[F[_]: Async](
      services: Services[F]
  ): HttpApi[F] =
    new HttpApi[F](services) {}
}
