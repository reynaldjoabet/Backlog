import cats.data.OptionT
import cats.effect._
import cats.effect.std.Supervisor

import org.http4s._

object CorrelationIDMiddleware {
//   def correlationIdMiddleware(routes: HttpRoutes[IO]): HttpRoutes[IO] =
//     Kleisli { (req: Request[IO]) =>
//       OptionT(
//         for
//           logger <- Slf4jLogger.create[IO]
//           correlationId <-
//             req.headers
//               .get(ci"X-Correlation-ID")
//               .map(_.head)
//               .fold(FULID[IO].generate)(x => IO.pure(x.value))
//           _ <- IO(MDC.put("CorrelationID", correlationId))
//           response <- routes(req).value
//           _ <- IO(MDC.remove("CorrelationID"))
//         yield response
//       )
//     }

//   // CorrelationID always present in MDC here
//   private val helloHandler: IO[Response[IO]] =
//     for
//       logger <- Slf4jLogger.create[IO]
//       _ <- logger.info("I'm inside a request handler")
//       res <- Ok("...and hello to you.")
//     yield res

//   // CorrelationID only sometimes present in MDC here
//   def pseudonymizeColumnHandler(request: Request[IO]): IO[Response[IO]] =
//     for
//       logger <- Slf4jLogger.create[IO]
//       eitherRes <- request.attemptAs[PseudonymizeRequest].value
//       // For debugging, sometimes I get None here
//       correlationId <- IO(MDC.get("CorrelationID"))
//       _ <- logger.info(s"The correlationId is $correlationId")
//       res <- eitherRes match
//         case Left(err) =>
//           val errMsg = s"Failed to decode JSON body. Cause ${err.cause}"
//           logger.error(errMsg) *> BadRequest(errMsg)
//         case Right(pseudReq) => logger.info(pseudReq.toString) *> Ok(
//             PseudonymizeResponse("Received :)").asJson
//           )
//     yield res

//   val routes: HttpRoutes[IO] = HttpRoutes.of {
//     case GET -> Root / "hello" => helloHandler
//     case req @ POST -> Root / "pseudonymize" / "column" =>
//       pseudonymizeColumnHandler(req)
//   }

//   val httpApp = correlationIdMiddleware(routes).orNotFound

// implement a resource-safe imperative client for :
  // 1 . sending data over a web socket connection
  // 2. so that releasing the client resource also shuts down all the underlying connections

  trait WebSocketClient {
    def sendMessage(msg: String): IO[Unit]
  }

  val clientResource: Resource[IO, WebSocketClient] = ???

  clientResource.use { client =>
    client.sendMessage("Helllo Scala conf")

  }

  Supervisor

}
