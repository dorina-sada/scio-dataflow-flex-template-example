package com.spotify.bq

import com.spotify.scio._
import com.spotify.scio.bigquery._
import com.spotify.common.ExampleData

// Read and write BigQuery `TableRow` JSON files
//sbt "runMain com.spotify.bq.TableRowJsonInOut \
//--project=${PROJECT} --runner=DataflowRunner --zone=${ZONE} \
//--input=gs://apache-beam-samples/wikipedia_edits/wiki_data-*.json \
//--output=gs://${BUCKET}/${FOLDER}/wikipedia"
object TableRowJsonInOut {
  def main(cmdlineArgs: Array[String]): Unit = {
    val (sc, args) = ContextAndArgs(cmdlineArgs)
    // Open text files a `SCollection[TableRow]`
    sc.tableRowJsonFile(args.getOrElse("input", ExampleData.EXPORTED_WIKI_TABLE))
      .take(100)
      // Save result as text files under the output path
      .saveAsTableRowJsonFile(args("output"))
    sc.run()
    ()
  }
}
