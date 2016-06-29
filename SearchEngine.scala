package search
import nlp.Parser
import index._
import Math.log

class SearchEngine(val index: Map[String, Seq[InvertedIndexEntry]]) {
  def search(query: String, limit: Int = 10, page: Int = 0): Ranking = {
    def startPage = page * limit
    def endPage = startPage + limit

    val words = Parser.toMainlyWords(query)
    if(words.length <= 0) {
      new Ranking(Seq.empty[Searchable], 0, 0)
    } else {
      val initSearchables = index.getOrElse(words(0), Seq.empty[InvertedIndexEntry]).map(_.searchable)
      val df = words.foldLeft(Map.empty[String, Int]) {
        (map, word) => map + (word -> index.getOrElse(word, Seq.empty[InvertedIndexEntry]).length)
      }
      val searchableIntersection = words.foldLeft(initSearchables) {
        (intersection, word) => intersection.intersect(index.getOrElse(word, Seq.empty[InvertedIndexEntry]).map(_.searchable))
      }.toSet
      val scores = words.foldLeft(Map.empty[String, Map[Searchable, Double]]) {
        (map, word) => map + (word -> index.getOrElse(word, Seq.empty[InvertedIndexEntry]).map {
            invertedIndexEntry => (invertedIndexEntry.searchable, keepScore(invertedIndexEntry, df(word)))
        }.toMap)
      }

      val result = searchableIntersection.foldLeft(Seq.empty[(Searchable, Double)]) {
        (seq, searchable) => seq :+ ((searchable, words.foldLeft(.0)((s, word) => s + scores(word)(searchable))))
      }.sortBy(_._2).reverse.map(_._1).slice(startPage, endPage)

      new Ranking(result, limit, page)
    }
  } 

  private def keepScore(invertedIndexEntry: InvertedIndexEntry, df: Int): Double =
      (invertedIndexEntry.titleTf * 2 + invertedIndexEntry.textTf) / log(df)
}

class Ranking(val ranking: Seq[Searchable], val limit: Int, val page: Int){
  def apply(i: Int) = ranking(i)
  val header = "\n========= Ranking =========\n"
  val footer = "===========================\n"
  override def toString = ranking.zipWithIndex.foldLeft(header) {
    case (str, (searchable, i)) => str + "[" + (i + page * limit) + "] " + searchable + "\n"
  } + footer
}
