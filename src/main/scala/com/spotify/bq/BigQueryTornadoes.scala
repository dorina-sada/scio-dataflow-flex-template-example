package com.spotify.bq

import com.google.api.services.bigquery.model.{TableFieldSchema, TableSchema}
import com.spotify.scio._
import com.spotify.scio.bigquery._
import com.spotify.common.ExampleData

import scala.jdk.CollectionConverters._

// `sbt "runMain com.spotify.scio.examples.cookbook.BigQueryTornadoes
// --project=[PROJECT] --runner=DataflowRunner --zone=[ZONE]
// --input=clouddataflow-readonly:samples.weather_stations
// --output=[DATASET].bigquery_tornadoes"`
object BigQueryTornadoes {
  def main(cmdlineArgs: Array[String]): Unit = {
    // Create `ScioContext` and `Args`
    val (sc, args) = ContextAndArgs(cmdlineArgs)

    // Schema for result BigQuery table
    val schema = new TableSchema().setFields(
      List(
        new TableFieldSchema().setName("month").setType("INTEGER"),
        new TableFieldSchema().setName("tornado_count").setType("INTEGER")
      ).asJava
    )

    // Open a BigQuery table as a `SCollection[TableRow]`
    val table = Table.Spec(args.getOrElse("input", ExampleData.WEATHER_SAMPLES_TABLE))
    sc.bigQueryTable(table)
      // Extract months with tornadoes
      .flatMap(r => if (r.getBoolean("tornado")) Some(r.getLong("month")) else None)
      // Count occurrences of each unique month to get `(Long, Long)`
      .countByValue
      // Map `(Long, Long)` tuples into result `TableRow`s
      .map(kv => TableRow("month" -> kv._1, "tornado_count" -> kv._2))
      // Save result as a BigQuery table
      .saveAsBigQueryTable(Table.Spec(args("output")), schema, WRITE_TRUNCATE, CREATE_IF_NEEDED)

    // Execute the pipeline
    sc.run()
    ()
  }
}
