package models

case class Task(id: Option[TaskId], title: String, description: String)
case class Task2(id: Option[TaskId], title: Option[String], description: Option[String])
