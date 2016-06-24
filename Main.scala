import file.FileManager
import nlp.Parser
import document.Document

object Main {
  def main(args: Array[String]):Unit = {

    val fileManager = new FileManager
    val sampleStrings = fileManager.readFile("./data/sample.txt")
    val documents = sampleStrings.map(new Document(_))
    println(documents.take(2).map(_.tf))
  }
}
