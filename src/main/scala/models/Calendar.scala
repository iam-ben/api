package models

import java.sql.Timestamp

case class Calendar(id: Option[CalendarId], title: String, description: String, date_start: Timestamp, date_end: Timestamp)
case class Calendar2(id: Option[CalendarId], title: Option[String], description: Option[String], date_start: Option[Timestamp], date_end: Option[Timestamp])
