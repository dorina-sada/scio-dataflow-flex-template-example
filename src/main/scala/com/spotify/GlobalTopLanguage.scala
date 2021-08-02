package com.spotify

/**
 * Apache Beam BigQueryIO connector is not type safe. (TableRow is Map<String, Object>)
 *   => possible run time error
 *  With Scio/Scala adds a type safe BigQuery which represent each row as a case class
 *   => compilation time
 * Scio uses Scala Macros Annotation and BigQuery dry-run
 *
 * Finding Top 10 Programming Languages by Repo Count
 *
 * The first macro annotation that we are going to cover is @BigQuery.fromTable which helps in reading rows from a BigQuery table using table name.
 *    bigquery-public-data.github_repos.languages
 *      => schema: repo_name(string); language(RECORD)
 */

//import com.spotify.scio._
//import com.spotify.scio.bigquery.types.BigQueryType
//import com.spotify.scio.bigquery._
//import com.spotify.scio.ContextAndArgs
//import com.spotify.scio.coders.Record
//import com.spotify.scio.coders._
//import com.spotify.scio.values.SCollection

object GlobalTopLanguage {

//  case class GithubLanguages(repo_name: String, language: Record[(String, Integer)])
//
//
//  @BigQueryType.fromTable("bigquery-public-data:github_repos.languages")
//  class Row
//
//
////  @BigQueryType.fromStorage(
////    "bigquery-public-data:github_repos.languages",
////    selectedFields = List("tornado", "month"),
////    rowRestriction = "tornado = true"
////  )
////  class Row
//
//  def parseRow(row: Row): Iterable[(String, (String,Long))] = {
//    val repoName = row.repo_name.getOrElse("DUMMY_REPO")
//    row.language.map(l => (repoName, (l.name.getOrElse("DUMMY_LANG"), l.bytes.getOrElse(0L))))
//  }
//
//  def main(cmdlineArgs: Array[String]): Unit = {
//
//    val (sc, args) = ContextAndArgs(cmdlineArgs)
//    implicit val order = Ordering.by[(String,Long),Long](_._2)
//    sc
//      .typedBigQueryTable[GithubLanguages](Table.Spec("bigquery-public-data:github_repos.languages"))
//
//    rows.flatMap(r => parseRow(r))
//      .maxByKey
//      .map(kv => (kv._2._1,kv._1))
//      .countByKey
//      .top(10)
//      .flatten
//      .map(v => v._1 + "," + v._2)
//      .saveAsTextFile(args("output"))
//
//    //sc.close()
//  }
}


