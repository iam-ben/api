import models.Task
import dao.TasksDao
import akka.http.scaladsl.model.{StatusCode, MediaTypes, HttpEntity}
import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

class TasksApiSpec extends BaseServiceSpec with ScalaFutures{
  "Tasks service" should {
    "retrieve tasks list" in {
      Get("/tasks") ~> tasksApi ~> check {
        responseAs[JsArray] should be(testTasks.toJson)
      }
    }
    "retrieve task by id" in {
      Get("/tasks/1") ~> tasksApi ~> check {
        responseAs[JsObject] should be(testTasks.head.toJson)
      }
    }
    "create task properly" in {
      val newTitle = "UpdatedTitle"
      val requestEntity = HttpEntity(MediaTypes.`application/json`,
        JsObject(
          "title" -> JsString(newTitle),
          "description" -> JsString(testTasks.head.description)
        ).toString())
      Post("/tasks", requestEntity) ~> tasksApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        Get("/tasks") ~> tasksApi ~> check {
          responseAs[Seq[Task]] should have length 4
        }
      }
    }
    "update task by id" in {
      val newTitle = "UpdatedTitle"
      val requestEntity = HttpEntity(MediaTypes.`application/json`,
        JsObject(
          "title" -> JsString(newTitle),
          "description" -> JsString(testTasks.head.description)
        ).toString())
      Put("/tasks/1", requestEntity) ~> tasksApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        whenReady(TasksDao.findById(1)) { result =>
          result.title should be(newTitle)
        }
      }
    }
    "delete task by id" in {
      Delete("/tasks/1") ~> tasksApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        Get("/tasks") ~> tasksApi ~> check {
          responseAs[Seq[Task]] should have length 3
        }
      }
    }
  }
}
