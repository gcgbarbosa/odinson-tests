package ai.lum.odinson.tests

import ai.lum.odinson.{ExtractorEngine, Mention, NamedCapture}

/** Queries an index with a rulefile
 *  @author gcgbarbosa
 */
object TestApp extends App {
  /** receives a [[ai.lum.odinson.Mention]] and prints its content
   */
  def getEventRuleResults(mention: Mention): Unit =  {
    // TODO: assert this is an EventMention
    // print trigger
    println(s"#trigger:<${ee.getString(mention.luceneDocId, mention.odinsonMatch)}>")
    // print named captures
    getNamedCapture(mention.odinsonMatch.namedCaptures, mention.luceneDocId)
  }
  /** receives a list of mentions and calls the single item function
   */
  def getEventRuleResults(mentions: Seq[Mention]): Unit =  {
    mentions.map(m => getEventRuleResults(m))
  }
  /** receives a [[ai.lum.odinson.NamedCapture]] and prints its content
   */
  def getNamedCapture(nc: NamedCapture, luceneDocId: Int): Unit =  {
    println(s"##named-capture ${nc.name}:<${ee.getString(luceneDocId, nc.capturedMatch)}>")
  }
  /** receives a list of named captures and calls the single item function 
   */
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
  // print the information found
  getEventRuleResults(mentions)
  //
}
