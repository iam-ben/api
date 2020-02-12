package api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import dao.CalendarsDao
import mappings.JsonMappings
import models.{Calendar, Calendar2}
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global

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
    (path("calendars"/IntNumber) & patch) { id => entity(as[Calendar2]) { calendar =>
      complete (CalendarsDao.update2(calendar, id).map(_.toJson))
      }
    }~
    (path("calendars"/IntNumber) & delete) { calendarId =>
      complete (CalendarsDao.delete(calendarId).map(_.toJson))
    }
}
