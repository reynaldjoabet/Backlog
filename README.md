# Backlog

 session cookies—cookies that do not explicitly set an expiration date with Max-Age or Expires—as these are instead cleared when the browsing session ends.

 __Secure- prefix: Cookies with names starting with __Secure- (dash is part of the prefix) must be set with the secure flag from a secure page (HTTPS).

__Host- prefix: Cookies with names starting with __Host- must be set with the secure flag, must be from a secure page (HTTPS), must not have a domain specified (and therefore, are not sent to subdomains), and the path must be /


```scala
 expires = None,
maxAge = None,
```
the csrf cookie is a session cookie as both the expires and maxAge are None. firefox indicates it as Session

`git fetch origin` fetches all remote branches

`git checkout -b routes origin/routes` is used to create a local branch based of the remote branch
branch 'routes' set up to track 'origin/routes'.
Switched to a new branch 'routes'


routing tables map specific routes to corresponding request handlers
This also involves manual decoding of headers, query parameters and request bodies
It also involves manual encoding of responses

```scala
// Define a type lambda that represents a function type with a higher-kinded type parameter
type MyLambda = ({ type Y[X] = Option[X] })#Y
// MyLambda is equivalent to Option[_]

val value: MyLambda[Int] = Some(42) // equivalent to Option[Int]

```

![](Screenshot1.png)
![](Screenshot2.png)
![](Screenshot3.png)
![](Screenshot4.png)

When a cookie is set with the domain `.localhost`, it indicates that the cookie should be sent to any subdomain of `localhost`, including `chat.localhost`

When a cookie is set for `api.example.com`, it is only accessible to requests made to `api.example.com` or its subdomains (e.g., sub.api.example.com). It will not be sent to requests made to `example.com` or any other domain.

If you want a cookie to be accessible by both `example.com` and `api.example.com`, you need to set the cookie with a domain attribute of `.example.com`. This leading dot indicates that the cookie is accessible to all subdomains of `example.com`
the Domain attribute specifies which domain and its subdomains can access the cookie. If you set the Domain attribute to .example.com, the cookie will be accessible to example
cookie, interceptors,


prometheus -> graphana




every step in github actions must define a uses or run key

OTel is used for instrumenting, generating, collecting, and exporting telemetry data such as traces, metrics, and logs.for instrumenting, generating, collecting, and exporting telemetry data such as traces, metrics, and logs.
 OpenTelemetry is focused on the generation, collection, management, and export of telemetry. the storage and visualization of telemetry is intentionally left to other tools.
 To make a system observable, it must be instrumented. That is, the code must emit traces, metrics, or logs. The instrumented data must then be sent to an observability backend
Using OpenTelemetry, you can instrument your code in two primary ways:

- Code-based solutions via official APIs and SDKs for most languages
- Zero-code solutions



OTEL SDK----->OTLP Exporter---> Jaeger
                           ---> Otel Collector
        -----> Zipkin Explorer---> Zipkin
        -----> Prometheus Exporter ---> Prometheus



Error handling in tapir is divided into three areas:

    - Error outputs: defined per-endpoint, used for errors handled by the business logic

    - Failed effects: exceptions which are not handled by the server logic (corresponds to 5xx responses)

    - Decode failures: format errors, when the input values can’t be decoded (corresponds to 4xx responses, or trying another endpoint)


While 1. is specific to an endpoint, handlers for 2. and 3. are typically the same for multiple endpoints, and are specified as part of the server’s interpreter options.


`import sttp.tapir.json.circe._`



The above import brings into scope the `jsonBody[T]` body input/output description, which creates a codec, given an in-scope circe Encoder/Decoder and a Schema

`import io.circe.Printer`


```scala
object MyTapirJsonCirce extends TapirJsonCirce {
  override def jsonPrinter: Printer = Printer.spaces2.copy(dropNullValues = true)
}```

When the client rovides an unsupported paramter or repeats the same paramter multiple times in its requesrs, in both cases the client request is not as expected and should be refused... 400 Bad request


```bash
tar cf targets.tar target project/target
```

tar: The command-line utility for manipulating tar archives.
cf: Options to the tar command:
c: Create a new archive.
f: Specify the filename of the archive.
targets.tar: The filename of the archive that will be created.
target project/target: The directories to be included in the archive. In this case, it includes both the target directory and the project/target directory


~/Library/Caches/Coursier/v1/github.com/	virtuslab/scala-cli



A term closely related to unit tests is code coverage. Code coverage is a metric that gives you an overview of how much of the program is covered with tests

Cached when sbt files are found (any of *.sbt, project/**.scala, project/**.sbt, project/build.properties).

[Code Coverage](https://diamantidis.github.io/2020/05/17/ci-with-github-actions-for-scala-project)



