import akka.http.scaladsl.server.Directives._
import api.{ApiErrorHandler, TasksApi, CalendarsApi}

trait Routes extends ApiErrorHandler with TasksApi with CalendarsApi {
  val routes =
    pathPrefix("v1") {
      tasksApi ~
      calendarsApi
    } ~ path("")(getFromResource("public/index.html"))
}
