package search

import nlp.Parser

object Email{
  var id = 0
  def getId = {
    val retId = id
    id += 1
    retId
  }
}

class Email(val title: String, val text: String) extends Searchable{
  val id = Email.getId
  val titleTf = getTf(title)
  val textTf = getTf(text)
  val words = titleTf.map(_._1).toSet ++ textTf.map(_._1).toSet
  override def toString = title
}
