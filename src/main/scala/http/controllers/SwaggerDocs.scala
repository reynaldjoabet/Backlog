package http.controllers

import cats.effect
import cats.effect.IO

import org.http4s.HttpRoutes
import services._
import sttp.apispec.openapi.Info
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.swagger.bundle.SwaggerInterpreter

object SwaggerDocs {

  val info: Info               = Info("Jira like API", "1.0")
  private val healthController = new HealthController()

  private val accessGroupController = new AccessGroupController[IO]()

  private val durationController =
    new DurationController[IO](new DurationService[IO] {})

  private val emailController = new EmailController[IO](new EmailService[IO] {})

  private val epicController = new EpicController[IO](new EpicService[IO] {})

  private val issueController = new IssueController[IO](new IssueService[IO] {})

  private val issuetypeController =
    new IssuetypeController[IO](new IssueTypeService[IO] {})

  private val priorityController =
    new PriorityController[IO](new PriorityService[IO] {})

  private val projectController =
    new ProjectController[IO](new ProjectService[IO] {})

  private val sprintController =
    new SprintController[IO](new SprintService[IO] {})

  private val sprintIssueController =
    new SprintIssueController[IO](new SprintIssueService[IO] {})

  private val statusController =
    new StatusController[IO](new StatusService[IO] {})

  private val systemUserController =
    new SystemUserController[IO](new SystemUserService[IO] {})

  private val teamController = new TeamController[IO](new TeamService[IO] {})

  val allServerEndpoints =
    healthController.routes ++ accessGroupController.routes ++ durationController
      .routes ++ emailController.routes

  epicController.routes ++ issueController.routes ++ issuetypeController
    .routes ++ priorityController.routes ++
    projectController.routes ++ sprintController.routes ++ sprintIssueController
      .routes ++ statusController.routes ++ systemUserController.routes ++ teamController.routes

  val allEndpoints =
    healthController.li ++ durationController.li ++
      epicController.li ++ issueController.li ++ issuetypeController.li ++ priorityController.li ++
      projectController.li ++ sprintController.li ++ sprintIssueController.li ++ statusController
        .li ++ systemUserController.li ++ teamController.li

  // private val swaggerEndpoints: List[ServerEndpoint[Any, IO]] =
  //   SwaggerInterpreter().fromServerEndpoints(allEndpoints, info)
  private val swaggerEndpoints: List[ServerEndpoint[Any, IO]] =
    SwaggerInterpreter().fromEndpoints(allEndpoints, info)

  val swaggerRoute: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(swaggerEndpoints)

}
