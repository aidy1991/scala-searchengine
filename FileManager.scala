package file

import scala.io.Source
import java.io.File
import com.github.tototoshi.csv._

class FileManager {
  def readFile(fileName: String): Seq[String] = Source.fromFile(fileName).mkString.split("\n")
  def readEmail(fileName: String): Seq[Seq[String]] = {
    val reader = CSVReader.open(new File(fileName))
    val emailData = reader.all()
    reader.close()
    emailData
  }
}
