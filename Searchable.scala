package searchable

import nlp.Parser

trait Searchable{
  val id: Int
  val title: String
  val text: String
  val titleTf: Map[String, Int]
  val textTf: Map[String, Int]
  val words: Set[String]
  private[searchable] def getTf(text: String): Map[String, Int] =
      Parser.toMainlyEndFormWords(text).foldLeft(Map.empty[String, Int]) { (map, word) =>
        map + (word -> (map.getOrElse(word, 0) + 1))
      }
}
