package api

import models.{Task,Task2}
import dao.TasksDao
import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import mappings.JsonMappings
import akka.http.scaladsl.server.Directives._
import spray.json._

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
    (path("tasks"/IntNumber) & put) { id => entity(as[Task2]) { task =>
        complete (TasksDao.update2(task, id).map(_.toJson))
      }
    }~
    (path("tasks"/IntNumber) & delete) { taskId =>
      complete (TasksDao.delete(taskId).map(_.toJson))
    }
}
