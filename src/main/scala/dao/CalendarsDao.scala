package dao

import models.{Calendar, CalendarId}
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future

object CalendarsDao extends BaseDao{
  def findAll: Future[Seq[Calendar]] = calendarsTable.result
  def findById(calendarId: CalendarId): Future[Calendar] = calendarsTable.filter(_.id === calendarId).result.head
  def create(calendar: Calendar): Future[CalendarId] = calendarsTable returning calendarsTable.map(_.id) += calendar
  def update(newCalendar: Calendar, calendarId: CalendarId): Future[Int] = calendarsTable.filter(_.id === calendarId)
    .map(calendar => (calendar.title, calendar.description, calendar.date_start, calendar.date_end))
    .update((newCalendar.title, newCalendar.description, newCalendar.date_start, newCalendar.date_end))

  def delete(calendarId: CalendarId): Future[Int] = calendarsTable.filter(_.id === calendarId).delete
}
