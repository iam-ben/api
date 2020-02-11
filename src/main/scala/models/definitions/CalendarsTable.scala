package models.definitions
import models.{Calendar, CalendarId}
import slick.driver.PostgresDriver.api._

class CalendarsTable(tag: Tag) extends Table[Calendar](tag, "calendars") {
  def id = column[CalendarId]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def description = column[String]("description")
  def date_start: Rep[String] = column[String]("date_start")
  def date_end: Rep[String] = column[String]("date_end")
  def * = (id.?, title, description, date_start, date_end) <> ((Calendar.apply _).tupled, Calendar.unapply)
}