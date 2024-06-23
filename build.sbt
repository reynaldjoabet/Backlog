// The simplest possible sbt build file is just one line:

scalaVersion := "2.13.12"

name := "Backlog"

version := "1.0"

val http4sVersion = "0.23.25"

val cirisVersion = "3.5.0"

val circeVersion = "0.14.6"

val catsEffectVersion = "3.4.11"
val fs2Version        = "3.9.4"
val redis4catsVersion = "1.5.2"
val flywayVersion     = "9.21.2"
val postgresVersion   = "42.7.1"
val doobieVersion     = "1.0.0-RC4"
val logbackVersion    = "1.4.14"
val pureConfigVersion = "0.17.5"
val javaMailVersion   = "1.6.2"

def circe(artifact: String): ModuleID =
  "io.circe" %% s"circe-$artifact" % circeVersion

def ciris(artifact: String): ModuleID = "is.cir" %% artifact % cirisVersion

def http4s(artifact: String): ModuleID =
  "org.http4s" %% s"http4s-$artifact" % http4sVersion

def tapir(artifact: String) =
  "com.softwaremill.sttp.tapir" %% s"tapir-$artifact" % "1.9.10"

val prometheusMetrics = "org.http4s" %% "http4s-prometheus-metrics" % "0.24.6"

val circeGenericExtras = circe("generic-extras")
val circeCore          = circe("core")
val circeGeneric       = circe("generic")
val cireParser         = "io.circe"         %% "circe-parser" % circeVersion
val retry              = "com.github.cb372" %% "cats-retry"   % "3.1.0"
val cirisCore          = ciris("ciris")
val catsEffect         = "org.typelevel"    %% "cats-effect"  % catsEffectVersion
val fs2                = "co.fs2"           %% "fs2-core"     % fs2Version
val http4sDsl          = http4s("dsl")
val http4sServer       = http4s("ember-server")
val http4sClient       = http4s("ember-client")

val http4sCirce = http4s("circe")

val doobie_hikari   = "org.tpolecat"  %% "doobie-hikari"   % doobieVersion
val postgres        = "org.postgresql" % "postgresql"      % postgresVersion
val flyway          = "org.flywaydb"   % "flyway-core"     % flywayVersion
val doobie          = "org.tpolecat"  %% "doobie-core"     % doobieVersion
val doobie_postgres = "org.tpolecat"  %% "doobie-postgres" % doobieVersion
val logback         = "ch.qos.logback" % "logback-classic" % logbackVersion % Runtime

val skunk = "org.tpolecat" %% "skunk-core" % "1.1.0-M3"

val auth0    = "com.auth0"    % "java-jwt"   % "4.2.2"
val javaMail = "com.sun.mail" % "javax.mail" % javaMailVersion

val redis4cats = "dev.profunktor" %% "redis4cats-effects" % redis4catsVersion

val redis4catsLog4cats =
  "dev.profunktor" %% "redis4cats-log4cats" % redis4catsVersion

val pureConfig = "com.github.pureconfig" %% "pureconfig" % pureConfigVersion

val tapirCore      = tapir("core")
val tapirJsonCirce = tapir("json-circe")

val tapirHttp4sServer = tapir("http4s-server")

//val tapirOpenApiDocs = tapir("openapi-docs")
val tapirSwagger = tapir("swagger-ui-bundle")

// https://mvnrepository.com/artifact/io.opentelemetry.javaagent/opentelemetry-javaagent-tooling
//val javaAgentExporter= "io.opentelemetry.javaagent" % "opentelemetry-javaagent-tooling" % "2.2.0-alpha" % "runtime"

// https://mvnrepository.com/artifact/io.opentelemetry/opentelemetry-exporter-otlp
val otlpExporter = "io.opentelemetry" % "opentelemetry-exporter-otlp" % "1.36.0"

// https://mvnrepository.com/artifact/io.opentelemetry/opentelemetry-exporter-jaeger
val jaegerExporter =
  "io.opentelemetry" % "opentelemetry-exporter-jaeger" % "1.34.1"

// https://mvnrepository.com/artifact/io.opentelemetry/opentelemetry-exporter-zipkin
val zipkinExporter =
  "io.opentelemetry" % "opentelemetry-exporter-zipkin" % "1.36.0"

// https://mvnrepository.com/artifact/io.opentelemetry/opentelemetry-exporter-prometheus
val prometheusExporter =
  "io.opentelemetry" % "opentelemetry-exporter-prometheus" % "1.36.0-alpha"

val `munit-cats-effect` = "org.typelevel" %% "munit-cats-effect-3" % "1.0.7"

val `http4s-munit` = "com.alejandrohdezma" %% "http4s-munit" % "0.15.1" % Test

val `http4s-otel4s` = "org.http4s" %% "http4s-otel4s-middleware" % "0.3.0"

libraryDependencies ++= Seq(
  cirisCore,
  http4sDsl,
  http4sServer,
  http4sClient,
  http4sCirce,
  circeCore,
  circeGeneric,
  logback,
  catsEffect,
  fs2,
  retry,
  redis4cats,
  cireParser,
  doobie_hikari,
  flyway,
  doobie,
  doobie_postgres,
  postgres,
  prometheusMetrics,
  skunk,
  auth0,
  javaMail,
  redis4cats,
  redis4catsLog4cats,
  pureConfig,
  tapirCore,
  tapirJsonCirce,
  tapirHttp4sServer,
  tapirSwagger
  // tapirOpenApiDocs
)

ThisBuild / semanticdbEnabled := true

lazy val tests = (project in file("tests"))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings
  ) //Create a separate subproject instead of using IntegrationTest and in addition avoid using itSettings",
//"1.9.0"

// sbt command-line shortcut
addCommandAlias("ci-integration", "Integration/testOnly -- -n integrationTest")

lazy val IntegrationTest = config("integration").extend(Test)

//...
lazy val tests2 = (project in file("tests2"))
  .configs(IntegrationTest)
  .settings(
    // Exclude integration tests by default (in ScalaTest)
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-l", "integrationTest"),
    // Include integration tests, by nullifying the above option
    IntegrationTest / testOptions := Seq.empty
  )
  .settings(
    // Enable integration tests
    inConfig(IntegrationTest)(Defaults.testTasks) // Defaults.itSettings
  )

lazy val integrationtest = (project in file("integrationtest"))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    testFrameworks += new TestFramework("munit.Framework")
    //  libraryDependencies ++= Seq(
    //    "com.scalameta" %% "munit"                       % "0.7.3" % "it,test",
    //    "com.whisk"     %% "docker-testkit-impl-spotify" % "0.9.9" % "it",
    //    "com.47deg"     %% "docker-testkit-munit"        % "0.1.0" % "it"
    //  )
  )
