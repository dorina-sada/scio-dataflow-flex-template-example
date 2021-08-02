package com.spotify.bq

import com.spotify.scio.bigquery._
import com.spotify.scio.ContextAndArgs
import com.spotify.common.ExampleData
import com.google.api.services.bigquery.model.{TableFieldSchema, TableSchema}

import scala.jdk.CollectionConverters._

//sbt "runMain com.spotify.bq.StorageBigQueryTornadoes \
//--project=${PROJECT} --runner=DataflowRunner --zone=${ZONE} \
//--input=clouddataflow-readonly:samples.weather_stations \
//--output=${DATASET}.bq_storage_tornadoes"
object StorageBigQueryTornadoes {
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
    sc.bigQueryStorage(
      table,
      selectedFields = List("tornado", "month"),
      rowRestriction = "tornado = true"
    ).map(_.getLong("month"))
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
