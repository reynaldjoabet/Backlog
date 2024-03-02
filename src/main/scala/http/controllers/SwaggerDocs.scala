package http.controllers
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import cats.effect.IO
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.server.ServerEndpoint
import sttp.apispec.openapi.Info
import cats.effect
import sttp.tapir.server.http4s.Http4sServerInterpreter
import org.http4s.HttpRoutes

object SwaggerDocs {
  val info: Info = Info("Jira like API", "1.0")
  private val healthController = new HealthController()

  private val accessGroupController = new AccessGroupController[IO]()

  private val durationController = new DurationController[IO]()

  private val emailController = new EmailController[IO]()

  private val epicController = new EpicController[IO]()

  private val issueController = new IssueController[IO]()

  private val issuetypeController = new IssuetypeController[IO]()

  private val priorityController = new PriorityController[IO]()

  private val projectController = new ProjectController[IO]()

  private val sprintController = new SprintController[IO]()

  private val sprintIssueController = new SprintIssueController[IO]()

  private val statusController = new StatusController[IO]()

  private val systemUserController = new SystemUserController[IO]()

  private val teamController = new TeamController[IO]()

  val allEndpoints =
    healthController.routes ++ accessGroupController.routes ++ durationController.routes ++ emailController.routes
  epicController.routes ++ issueController.routes ++ issuetypeController.routes ++ priorityController.routes ++
    projectController.routes ++ sprintController.routes ++ sprintIssueController.routes ++ statusController.routes ++ systemUserController.routes ++ teamController.routes

  private val swaggerEndpoints: List[ServerEndpoint[Any, IO]] =
    SwaggerInterpreter().fromServerEndpoints(allEndpoints, info)

  val swaggerRoute: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(swaggerEndpoints)
}
