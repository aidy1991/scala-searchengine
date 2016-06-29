package index

import search.Searchable

object Indexer {
  def indexing(targets: Seq[Searchable]): Map[String, Seq[InvertedIndexEntry]] =
      targets.foldLeft(Seq.empty[(String, InvertedIndexEntry)]) {
    (seq, target) => seq ++ getInvertedIndexEntryMap(target)
  }.foldLeft(Map.empty[String, Seq[InvertedIndexEntry]]) {
    case (map, (word, entry)) => map + (word -> (map.getOrElse(word, Nil) :+ entry))
  }

  private def getInvertedIndexEntryMap(target: Searchable): Seq[(String, InvertedIndexEntry)] =
      target.words.foldLeft(Seq.empty[(String, InvertedIndexEntry)]) {
    case (seq, word) => seq :+ (word -> new InvertedIndexEntry(target, target.titleTf.getOrElse(word, 0), target.textTf.getOrElse(word, 0)))
  }
}

class InvertedIndexEntry(val searchable: Searchable, val titleTf: Int, val textTf: Int) {
  var df = 0
}
