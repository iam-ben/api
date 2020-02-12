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

  def update2(newTask: Task2, taskId: TaskId): Future[Int] = {
    val q = tasksTable.filter(_.id === taskId)
    (newTask.title, newTask.description) match {
        case (Some(_), Some(_)) =>
          q.map(p=> (p.title, p.description)).update(newTask.title.get, newTask.description.get)
        case (Some(_), None) =>
          q.map(p=> (p.title)).update(newTask.title.get)
        case (None, Some(_)) =>
          q.map(p=> (p.description)).update(newTask.description.get)
    }
  }

  def delete(taskId: TaskId): Future[Int] = tasksTable.filter(_.id === taskId).delete
}
