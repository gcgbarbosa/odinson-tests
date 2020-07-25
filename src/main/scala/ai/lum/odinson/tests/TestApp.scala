package ai.lum.odinson.tests

import ai.lum.odinson.{ExtractorEngine}

object TestApp extends App {
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
  val m0 = mentions.head
  // make a function to print this information
  println(s"Printing info from the first match")
  println(s"trigger: ${ ee.getString(m0.luceneDocId, m0.odinsonMatch) }")
  val nc0 = m0.odinsonMatch.namedCaptures.head
  println(s"named-capture <${nc0.name}>: ${ee.getString(m0.luceneDocId, nc0.capturedMatch)}")
  //
}
