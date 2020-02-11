package dao

import models.{Task, Task2, TaskId}
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future

object TasksDao extends BaseDao {
  def findAll: Future[Seq[Task]] = tasksTable.result
  def findById(taskId: TaskId): Future[Task] = tasksTable.filter(_.id === taskId).result.head
  def create(task: Task): Future[TaskId] = tasksTable returning tasksTable.map(_.id) += task
  def update(newTask: Task, taskId: TaskId): Future[Int] = tasksTable.filter(_.id === taskId)
    .map(task => (task.title, task.description))
    .update((newTask.title, newTask.description))
  def update2(newTask: Task2, taskId: TaskId): Future[Int] = { tasksTable.filter(_.id === taskId)
    .map(task => (task.title, task.description))
    .update((newTask.title, newTask.description))

/*    val x = tasksTable.filter(_.id === taskId)
    val y = x.map(task => (task.title, task.description))
    y.update((newTask.title, newTask.description)) */
    /*y.update(
      (newTask.title, newTask.description) match {
        case (Some(_), Some(_)) => (newTask.title.get, newTask.description.get)
        case (Some(_), None)    => (newTask.title.get)
        case (None   , Some(_)) => (newTask.description.get)
      }
    )*/
  }
   // .map(task => (task.title, task.description))
   // .update((newTask.title, newTask.description))
  /*val fullQuery:??? = partialQuery.update {
    (name, age) match {
      case (Some(_), Some(_)) => (name.get, age.get)
      case (Some(_), None)    => (name.get)
      case (None   , Some(_)) => (age.get)
    }
  }

  people.filter(_.name === "M Odersky")
    .map(p =>
      (name, age) match {
        case (Some(_), Some(_)) => (p.name, p.age)
        case (Some(_), None)    => (p.name)
        case (None   , Some(_)) => (p.age)
      }
    )
    .update(
      (name, age) match {
        case (Some(_), Some(_)) => (name.get, age.get)
        case (Some(_), None)    => (name.get)
        case (None   , Some(_)) => (age.get)
      }
    )
    .update((newTask.title, newTask.description))*/
  def delete(taskId: TaskId): Future[Int] = tasksTable.filter(_.id === taskId).delete
}
