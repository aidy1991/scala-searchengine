package file

import scala.io.Source

class FileManager {
  def readFile(fileName: String): Seq[String] = Source.fromFile(fileName).mkString.split("\n")
}
