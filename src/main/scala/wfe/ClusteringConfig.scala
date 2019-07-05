package wfe

import com.typesafe.config.ConfigFactory

object ClusteringConfig {
  private val config = ConfigFactory.load()

  val clusterName: String = config.getString("clustering.cluster.name")

}
