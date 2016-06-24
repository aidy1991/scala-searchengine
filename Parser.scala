package nlp

import org.chasen.mecab.MeCab
import org.chasen.mecab.Tagger
import org.chasen.mecab.Node
import scala.collection.mutable.ListBuffer

object Parser{
  def toWordNodes(str: String): Seq[WordNode] = {
    System.loadLibrary("MeCab");

    val wordBuf = ListBuffer.empty[WordNode]
    val words = Seq[WordNode]()
    val tagger = new Tagger();

    tagger.parse(str);

    var node = tagger.parseToNode(str);
    while(node != null){
      if(node.getSurface() != ""){
        wordBuf += new WordNode(node.getSurface(), node.getFeature())
      }
      node = node.getNext()
    }

    wordBuf.seq
  }

  def toWords(str: String): Seq[String] = toWordNodes(str).map(_.word)
  def toMainlyWords(str: String): Seq[String] = toWordNodes(str).filter(_.isMainly).map(_.word)
  def toMainlyEndFormWords(str: String): Seq[String] = toWordNodes(str).filter(_.isMainly).map(_.endForm)

  class WordNode(val word: String, val feature: String){
    val features = feature.split(",")
    val wordType = getWordType(features(0))
    val endForm = if(features.size > 7) features(6) else  word
    val katakana = if(features.size > 7) features(7) else word

    private def isNoun = this.wordType == WordNode.NOUN
    private def isVerb = this.wordType == WordNode.VERB
    private def isAdjective = this.wordType == WordNode.ADJECTIVE


    private def getWordType(wordTypeString: String) = wordTypeString match {
      case "名詞" => WordNode.NOUN
      case "動詞" => WordNode.VERB
      case "形容詞" => WordNode.ADJECTIVE
      case _ => WordNode.UNKNOWN
    }

    def isMainly = this.isNoun || this.isVerb || this.isAdjective

    override def toString = word + "/" + wordType
  }

  object WordNode{
    val NOUN = 0;
    val VERB = 1;
    val ADJECTIVE = 2;
    val UNKNOWN = 10;
  }
}
