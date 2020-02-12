package mappings
import java.sql.Timestamp
import java.text._

import models.{Calendar, Calendar2, Task, Task2}
import spray.json.{DefaultJsonProtocol, _}

import scala.util.Try

trait JsonMappings extends DefaultJsonProtocol {

  implicit object DateFormat extends JsonFormat[Timestamp] {
    def write(date: Timestamp) = JsString(dateToIsoString(date))
      def read(json: JsValue) = json match {
        case JsString(rawDate) =>
          parseIsoDateString(rawDate)
            .fold(deserializationError(s"Expected ISO Date format, got $rawDate"))(identity)
        case error => deserializationError(s"Expected JsString, got $error")
      }
  }

  private val localIsoDateFormatter = new ThreadLocal[SimpleDateFormat] {
    override def initialValue() = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  }

  private def dateToIsoString(date: Timestamp) =
    localIsoDateFormatter.get().format(date)
  private def parseIsoDateString(date: String): Option[Timestamp] =
    Try{
      val x = localIsoDateFormatter.get().parse(date)
      new java.sql.Timestamp(x.getTime)
    }.toOption

  implicit val taskFormat = jsonFormat3(Task)
  implicit val task2Format = jsonFormat3(Task2)
  implicit val calendarFormat = jsonFormat5(Calendar)
  implicit val calendar2Format = jsonFormat5(Calendar2)
}