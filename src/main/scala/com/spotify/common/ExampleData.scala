package com.spotify.common

object ExampleData {
  val SHAKESPEARE_ALL = "gs://apache-beam-samples/shakespeare/*"
  val KING_LEAR = "gs://apache-beam-samples/shakespeare/kinglear.txt"
  val OTHELLO = "gs://apache-beam-samples/shakespeare/othello.txt"

  val EXPORTED_WIKI_TABLE = "gs://apache-beam-samples/wikipedia_edits/*.json"
  val MONTHS = "gs://dataflow-samples/samples/misc/months.txt"
  val TRAFFIC =
    "gs://apache-beam-samples/traffic_sensor/Freeways-5Minaa2010-01-01_to_2010-02-15_test2.csv"
  val GAMING = "gs://apache-beam-samples/game/gaming_data*.csv"

  val WEATHER_SAMPLES_TABLE = "clouddataflow-readonly:samples.weather_stations"
  val SHAKESPEARE_TABLE = "bigquery-public-data:samples.shakespeare"
  val EVENT_TABLE = "clouddataflow-readonly:samples.gdelt_sample"
  val COUNTRY_TABLE = "gdelt-bq:full.crosswalk_geocountrycodetohuman"
}
