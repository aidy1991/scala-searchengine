import file.FileManager
import search._
import index.Indexer

object Main {
  def main(args: Array[String]):Unit = {

    val emailStrings = FileManager.readEmail("./data/mail1.csv")
    val emails = emailStrings.map(emailString => new Email(emailString(0), emailString(5)))

    emails.foreach(email => println(email.title +  ": " + email.text))

    val invertedIndex = Indexer.indexing(emails)
    println("-- Inverted Index")
    println(invertedIndex)

  }
}
