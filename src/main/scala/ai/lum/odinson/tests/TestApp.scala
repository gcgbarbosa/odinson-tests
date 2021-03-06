package ai.lum.odinson.tests

import ai.lum.odinson.{ExtractorEngine, Mention, NamedCapture}
import scala.io.Source

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
  // TODO: run this 10k times
  println("Starting odinson-tests...")
  // get the extractor engine
  val ee = ExtractorEngine.fromConfig
  val rr = ee.ruleReader
  // load rule file from resources
  val rulesResource = getClass.getResourceAsStream("/grammars/umbc.yml")
  val rules = Source.fromInputStream(rulesResource).getLines.mkString("\n")
  // compile query
  val queries = rr.compileRuleFile(rules)
  var i: Int = 0
  while(true) {
    i=i+1
    // extract mentions
    val mentions = ee.extractMentions(queries)
    // TODO: Print stuff
    // getEventRuleResults(mentions)
    println(s"Run: <${i.toString}> Found <${mentions.size}> mentions")
  }
}
