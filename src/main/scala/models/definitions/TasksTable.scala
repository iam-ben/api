package models.definitions

import models.{Task, TaskId}
import slick.driver.PostgresDriver.api._

class TasksTable(tag: Tag) extends Table[Task](tag, "tasks"){
  def id = column[TaskId]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def description = column[String]("description")
  def * = (id.?, title, description) <> ((Task.apply _).tupled, Task.unapply)
}