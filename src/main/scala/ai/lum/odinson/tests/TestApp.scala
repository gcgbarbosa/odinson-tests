package ai.lum.odinson.tests

import ai.lum.odinson.{ExtractorEngine, Mention, NamedCapture}

object TestApp extends App {
  def getEventRuleResults(mention: Mention): Unit =  {
    // print trigger
    println(s"#trigger:<${ee.getString(mention.luceneDocId, mention.odinsonMatch)}>")
    // print named captures
    getNamedCapture(mention.odinsonMatch.namedCaptures, mention.luceneDocId)
  }
  // print array of mentions 
  def getEventRuleResults(mentions: Seq[Mention]): Unit =  {
    mentions.map(m => getEventRuleResults(m))
  }
  // print named capture info
  def getNamedCapture(nc: NamedCapture, luceneDocId: Int): Unit =  {
    println(s"##named-capture ${nc.name}:<${ee.getString(luceneDocId, nc.capturedMatch)}>")
  }
  // print named capture lists
  def getNamedCapture(ncs: Seq[NamedCapture], luceneDocId: Int): Unit =  {
    ncs.map(nc => getNamedCapture(nc, luceneDocId))
  }
  // 
  println("starting odinson-tests...")
  // get the extractor engine
  val ee = ExtractorEngine.fromConfig
  val rr = ee.ruleReader
  // TODO: load rule file
  // for now, running a single basic basic rule
  val rules = """
    |vars:
    |  chunk: "[chunk=B-NP][chunk=I-NP]*"
    |
    |rules:
    |  - name: testrule
    |    type: event
    |    pattern: |
    |      trigger = [lemma=eat]
    |      subject: ^NP = >nsubj ${chunk}
    |      object: ^NP = >dobj ${chunk}
    """.stripMargin
  // compile query
  val queries = rr.compileRuleFile(rules)
  // extract mentions
  val mentions = ee.extractMentions(queries)
  // print everything that there is
  println(s"Found ${mentions.size} mentions.")
  // get the first match
  // TODO: make a function to print this information
  println(s"Printing matched info")
  getEventRuleResults(mentions)
  //
}
