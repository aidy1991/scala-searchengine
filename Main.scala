import file.FileManager
import nlp.Parser
import searchable._

object Main {
  def main(args: Array[String]):Unit = {

    val fileManager = new FileManager
    val emailStrings = fileManager.readEmail("./data/mail1.csv")
    val emails = emailStrings.map(emailString => new Email(emailString(0), emailString(5)))
    emails.foreach(email => println(email.title))
    //val documents = sampleStrings.map(new Document(_))
    //println(documents.take(2).map(_.textTf))
  }
}
