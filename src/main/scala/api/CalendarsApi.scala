package api

import models.Calendar
import dao.CalendarsDao
import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import mappings.JsonMappings
import akka.http.scaladsl.server.Directives._
import spray.json._

trait CalendarsApi extends JsonMappings {
  val calendarsApi =
    (path("calendars") & get ) {
      complete (CalendarsDao.findAll.map(_.toJson))
    }~
    (path("calendars"/IntNumber) & get) { id =>
      complete (CalendarsDao.findById(id).map(_.toJson))
    }~
    (path("calendars") & post) { entity(as[Calendar]) { calendar =>
      complete (CalendarsDao.create(calendar).map(_.toJson))
      }
    }~
    (path("calendars"/IntNumber) & put) { id => entity(as[Calendar]) { calendar =>
      complete (CalendarsDao.update(calendar, id).map(_.toJson))
      }
    }~
    (path("calendars"/IntNumber) & delete) { calendarId =>
      complete (CalendarsDao.delete(calendarId).map(_.toJson))
    }
}
