package mappings

import models.{Task,Calendar}
import spray.json.DefaultJsonProtocol

trait JsonMappings extends DefaultJsonProtocol {
  implicit val taskFormat = jsonFormat3(Task)
  implicit val calendarFormat = jsonFormat5(Calendar)
}