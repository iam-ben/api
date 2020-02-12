package api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import dao.TasksDao
import mappings.JsonMappings
import models.{Task,Task2}
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global

trait TasksApi extends JsonMappings {
  val tasksApi =
    (path("tasks") & get ) {
      complete (TasksDao.findAll.map(_.toJson))
    }~
    (path("tasks"/IntNumber) & get) { id =>
      complete (TasksDao.findById(id).map(_.toJson))
    }~
    (path("tasks") & post) { entity(as[Task]) { task =>
      complete (TasksDao.create(task).map(_.toJson))
      }
    }~
    (path("tasks"/IntNumber) & put) { id => entity(as[Task]) { task =>
        complete (TasksDao.update(task, id).map(_.toJson))
      }
    }~
    (path("tasks"/IntNumber) & patch) { id => entity(as[Task2]) { task =>
        complete (TasksDao.update2(task, id).map(_.toJson))
      }
    }~
    (path("tasks"/IntNumber) & delete) { taskId =>
      complete (TasksDao.delete(taskId).map(_.toJson))
    }
}
