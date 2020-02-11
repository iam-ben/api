import dao.BaseDao
import models.Task
import models.Calendar

import akka.event.{ NoLogging, LoggingAdapter }
import akka.http.scaladsl.testkit.ScalatestRouteTest
import utils.MigrationConfig
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration._

trait BaseServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with Routes with MigrationConfig with BaseDao{
  protected val log: LoggingAdapter = NoLogging

  import driver.api._

  val testTasks = Seq(
    Task(Some(1), "title1", "description 1"),
    Task(Some(2), "title2", "description 2"),
    Task(Some(3), "title3", "description 3")
  )

  val testCalendars = Seq(
    Calendar(Some(1), "title1", "content 1", "2020-02-01 05:30:00", "2020-02-03 05:30:00"),
    Calendar(Some(1), "title2", "content 2", "2020-02-01 08:30:00", "2020-02-04 08:30:00"),
    Calendar(Some(1), "title3", "content 3", "2020-02-01 12:30:00", "2020-02-05 12:30:00")
  )

  reloadSchema()
  Await.result(tasksTable ++= testTasks, 10.seconds)
  Await.result(calendarsTable ++= testCalendars, 10.seconds)
}
