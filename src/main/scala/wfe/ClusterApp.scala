package wfe

import akka.actor.{ActorSystem, Props}
import akka.cluster.Cluster
import akka.cluster.sharding.ClusterSharding
import wfe.ProcessManager.{Processes, parseProcess}

import scala.concurrent.duration._
import scala.io.Source

object ClusterApp extends App {
  import ClusteringConfig._

  implicit val system = ActorSystem(clusterName)
  val cluster = Cluster(system)

  val sharding = ClusterSharding(system)

  val clusterListener = system.actorOf(Props[ClusterListener], name = "clusterListener")

  sys.addShutdownHook(system.terminate())


  system.scheduler.scheduleOnce(10 second) {
    val parallelJoin = parseProcess(Source.fromResource("string.xml").mkString)
    val processManager = system.actorOf(Props(classOf[Processes]), "processmanager")
    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], parallelJoin), "process1")
    cluster.state.getLeader
    cluster.state.getLeader.
  }(system.getDispatcher)
//
//  sys.addShutdownHook(system.terminate())

}

