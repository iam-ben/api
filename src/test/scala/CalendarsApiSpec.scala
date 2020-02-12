import models.Calendar
import dao.CalendarsDao
import akka.http.scaladsl.model.{StatusCode, MediaTypes, HttpEntity}
import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

class CalendarsApiSpec extends BaseServiceSpec with ScalaFutures{
  "Calendars service" should {
    "retrieve calendars list" in {
      Get("/calendars") ~> calendarsApi ~> check {
        responseAs[JsArray] should be(testCalendars.toJson)
      }
    }
    "retrieve calendar by id" in {
      Get("/calendar/1") ~> calendarsApi ~> check {
        responseAs[JsObject] should be(testCalendars.head.toJson)
      }
    }
    "create calendar properly" in {
      val newTitle = "UpdatedTitle"
      val requestEntity = HttpEntity(MediaTypes.`application/json`,
        JsObject(
          "title" -> JsString(newTitle),
          "description" -> JsString(testCalendars.head.description),
          "date_start" -> JsString(testCalendars.head.date_start.toString()),
          "date_end" -> JsString(testCalendars.head.date_end.toString())
        ).toString())
      Post("/calendars", requestEntity) ~> calendarsApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        Get("/calendars") ~> calendarsApi ~> check {
          responseAs[Seq[Calendar]] should have length 4
        }
      }
    }
    "update calendar by id" in {
      val newTitle = "UpdatedTitle"
      val requestEntity = HttpEntity(MediaTypes.`application/json`,
        JsObject(
          "title" -> JsString(newTitle),
          "description" -> JsString(testCalendars.head.description),
          "date_start" -> JsString(testCalendars.head.date_start.toString()),
          "date_end" -> JsString(testCalendars.head.date_end.toString())
        ).toString())
      Put("/calendars/1", requestEntity) ~> calendarsApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        whenReady(CalendarsDao.findById(1)) { result =>
          result.title should be(newTitle)
        }
      }
    }
    "delete calendar by id" in {
      Delete("/calendars/1") ~> calendarsApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        Get("/calendars") ~> calendarsApi ~> check {
          responseAs[Seq[Calendar]] should have length 3
        }
      }
    }
  }
}
