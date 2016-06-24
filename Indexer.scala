package index

import document.Document

object Indexer{
  def indexing(documents: Seq[Document]): Map[String, Seq[InvertedIndexEntry]] =
      documents.foldLeft(Seq.empty[(String, InvertedIndexEntry)]) {
    (seq, document) => seq ++ getInvertedIndexEntryMap(document)
  }.foldLeft(Map.empty[String, Seq[InvertedIndexEntry]]) {
    case (map, (word, entry)) => map + (word -> (map.getOrElse(word, Nil) :+ entry))
  }
  private def getInvertedIndexEntryMap(document: Document): Seq[(String, InvertedIndexEntry)] =
      document.tf.foldLeft(Seq.empty[(String, InvertedIndexEntry)]) {
    case (seq, (word, tfValue)) => seq :+ (word -> new InvertedIndexEntry(document.docId, tfValue))
  }

}

class InvertedIndexEntry(val docId: Int, val tf: Int)
