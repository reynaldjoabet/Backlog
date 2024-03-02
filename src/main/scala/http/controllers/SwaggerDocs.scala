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

  private val accessGroupController = new AccessGroupController()

  private val durationController = new DurationController()

  private val emailController = new EmailController()

  private val epicController = new EpicController()

  private val issueController = new IssueController()

  private val issuetypeController = new IssuetypeController()

  private val priorityController = new PriorityController()

  private val projectController = new ProjectController()

  private val sprintController = new SprintController()

  private val sprintIssueController = new SprintIssueController()

  private val statusController = new StatusController()

  private val systemUserController = new SystemUserController()

  private val teamController = new TeamController()

  val allEndpoints =
    healthController.routes ++ accessGroupController.routes ++ durationController.routes ++ emailController.routes
  epicController.routes ++ issueController.routes ++ issuetypeController.routes ++ priorityController.routes ++
    projectController.routes ++ sprintController.routes ++ sprintIssueController.routes ++ statusController.routes ++ systemUserController.routes ++ teamController.routes

  private val swaggerEndpoints: List[ServerEndpoint[Any, IO]] =
    SwaggerInterpreter().fromServerEndpoints(allEndpoints, info)

  val swaggerRoute: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(swaggerEndpoints)
}
