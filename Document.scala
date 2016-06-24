package document

import nlp.Parser

object Document{
  var docId = 0
  def getDocId = {
    val retDocId = docId
    docId += 1
    retDocId
  }
}

class Document(val text: String){
  val docId = Document.getDocId
  val tf = getTf

  private def getTf: Map[String, Int] =
      Parser.toMainlyEndFormWords(text).foldLeft(Map.empty[String, Int]) { (map, word) =>
        map + (word -> (map.getOrElse(word, 0) + 1))
      }

}
