package index

import searchable._

object Indexer{
  def indexing(targets: Seq[Searchable]): Map[String, Seq[InvertedIndexEntry]] =
      targets.foldLeft(Seq.empty[(String, InvertedIndexEntry)]) {
    (seq, target) => seq ++ getInvertedIndexEntryMap(target)
  }.foldLeft(Map.empty[String, Seq[InvertedIndexEntry]]) {
    case (map, (word, entry)) => map + (word -> (map.getOrElse(word, Nil) :+ entry))
  }
  private def getInvertedIndexEntryMap(target: Searchable): Seq[(String, InvertedIndexEntry)] =
      target.words.foldLeft(Seq.empty[(String, InvertedIndexEntry)]) {
    case (seq, word) => seq :+ (word -> new InvertedIndexEntry(target.id, target.titleTf(word), target.textTf(word)))
  }

}

class InvertedIndexEntry(val id: Int, val titleTf: Int, val textTf: Int)
