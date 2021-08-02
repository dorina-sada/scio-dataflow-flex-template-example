package com.spotify.bq

import com.spotify.scio.bigquery._
import com.spotify.scio.ContextAndArgs
//import com.spotify.scio.values.SCollection
import com.spotify.scio.coders._

//sbt "runMain com.spotify.bq.TypedBigQueryTornadoes \
//--project=${PROJECT} --runner=DataflowRunner --zone=${ZONE} \
//--output=${DATASET}.bq_typed_tornadoes"
object TypedBigQueryTornadoes {
  // Annotate input class with schema inferred from a BigQuery SELECT.
  // Class `Row` will be expanded into a case class with fields from the SELECT query. A companion
  // object will also be generated to provide easy access to original query/table from annotation,
  // `TableSchema` and converter methods between the generated case class and `TableRow`.
  @BigQueryType.fromQuery("SELECT tornado, month FROM [bigquery-public-data:samples.gsod]")
  class Row

  // Annotate output case class.
  // Note that the case class is already defined and will not be expanded. Only the companion
  // object will be generated to provide easy access to `TableSchema` and converter methods.
  @BigQueryType.toTable
  case class Result(month: Long, tornado_count: Long)

  def main(cmdlineArgs: Array[String]): Unit = {
    val (sc, args) = ContextAndArgs(cmdlineArgs)

    // Get input from BigQuery and convert elements from `TableRow` to `Row`.
    // SELECT query from the original annotation is used by default.
    sc.typedBigQuery[Row]()
      .flatMap(r => if (r.tornado.getOrElse(false)) Seq(r.month) else Nil)
      .countByValue
      .map(kv => Result(kv._1, kv._2))
      // Convert elements from Result to TableRow and save output to BigQuery.
      .saveAsTypedBigQueryTable(
        Table.Spec(args("output")),
        writeDisposition = WRITE_TRUNCATE,
        createDisposition = CREATE_IF_NEEDED
      )

    sc.run()
    ()
  }
}
