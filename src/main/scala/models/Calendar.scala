package models

case class Calendar(id: Option[CalendarId], title: String, description: String, date_start: String, date_end: String)
