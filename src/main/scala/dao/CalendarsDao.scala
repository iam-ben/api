package dao

import java.text.SimpleDateFormat

import models.{Calendar, Calendar2, CalendarId}
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future

object CalendarsDao extends BaseDao{
  val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss','SSS")

  def findAll: Future[Seq[Calendar]] = calendarsTable.result
  def findById(calendarId: CalendarId): Future[Calendar] = calendarsTable.filter(_.id === calendarId).result.head
  def create(calendar: Calendar): Future[CalendarId] = calendarsTable returning calendarsTable.map(_.id) += calendar
  def update(newCalendar: Calendar, calendarId: CalendarId): Future[Int] = calendarsTable.filter(_.id === calendarId)
    .map(calendar => (calendar.title, calendar.description, calendar.date_start , calendar.date_end))
    .update((newCalendar.title, newCalendar.description, newCalendar.date_start, newCalendar.date_end))

  def update2(newCalendar: Calendar2, calendarId: CalendarId): Future[Int] = {
    val q = calendarsTable.filter(_.id === calendarId)
    (newCalendar.title, newCalendar.description, newCalendar.date_start, newCalendar.date_end) match {
      case (Some(_), Some(_), Some(_), Some(_)) =>
        q.map(p=> (p.title, p.description, p.date_start, p.date_end)).update(newCalendar.title.get, newCalendar.description.get, newCalendar.date_start.get, newCalendar.date_end.get)
      case (Some(_), Some(_), Some(_), None) =>
        q.map(p=> (p.title, p.description, p.date_start)).update(newCalendar.title.get, newCalendar.description.get, newCalendar.date_start.get)
      case (Some(_), Some(_), None, Some(_)) =>
        q.map(p=> (p.title, p.description, p.date_end)).update(newCalendar.title.get, newCalendar.description.get, newCalendar.date_end.get)
      case (Some(_), Some(_), None, None) =>
        q.map(p=> (p.title, p.description)).update(newCalendar.title.get, newCalendar.description.get)

      case (Some(_), None, Some(_), Some(_)) =>
        q.map(p=> (p.title, p.date_start, p.date_end)).update(newCalendar.title.get, newCalendar.date_start.get, newCalendar.date_end.get)
      case (Some(_), None, Some(_), None) =>
        q.map(p=> (p.title, p.date_start)).update(newCalendar.title.get, newCalendar.date_start.get)
      case (Some(_), None, None, Some(_)) =>
        q.map(p=> (p.title, p.date_end)).update(newCalendar.title.get, newCalendar.date_end.get)
      case (Some(_), None, None, None) =>
        q.map(p=> (p.title)).update(newCalendar.title.get)

      case (None, Some(_), Some(_), Some(_)) =>
        q.map(p=> (p.description, p.date_start, p.date_end)).update(newCalendar.description.get, newCalendar.date_start.get, newCalendar.date_end.get)
      case (None, Some(_), Some(_), None) =>
        q.map(p=> (p.description, p.date_start)).update(newCalendar.description.get, newCalendar.date_start.get)
      case (None, Some(_), None, Some(_)) =>
        q.map(p=> (p.description, p.date_end)).update(newCalendar.description.get, newCalendar.date_end.get)
      case (None, Some(_), None, None) =>
        q.map(p=> (p.description)).update(newCalendar.description.get)

      case (None, None, Some(_), Some(_)) =>
        q.map(p=> (p.date_start, p.date_end)).update(newCalendar.date_start.get, newCalendar.date_end.get)
      case (None, None, Some(_), None) =>
        q.map(p=> (p.date_start)).update(newCalendar.date_start.get)
      case (None, None, None, Some(_)) =>
        q.map(p=> (p.date_end)).update(newCalendar.date_end.get)
    }
  }

  def delete(calendarId: CalendarId): Future[Int] = calendarsTable.filter(_.id === calendarId).delete
}
