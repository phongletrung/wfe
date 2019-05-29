package wfe

import akka.actor.{ActorSystem, Props}
import akka.cluster.Cluster
import wfe.ProcessManager.Processes

import scala.concurrent.duration._
import scala.io.Source

object ClusterApp extends App {
  import ClusteringConfig._

  implicit val system: ActorSystem = ActorSystem(clusterName)
  implicit val cluster: Cluster = Cluster(system)

  val clusterListener = system.actorOf(Props[ClusterListener], name = "clusterListener")

  sys.addShutdownHook(system.terminate())

  system.scheduler.scheduleOnce(5 second) {
    if (cluster.selfAddress.equals(cluster.state.getLeader)) {
      val processAsString = Source.fromResource("orJoin.xml").mkString
      val processManager = system.actorOf(Props(classOf[Processes]), "processmanager")
      processManager ! Processes.CreateProcess(Props(classOf[ProcessInstanceActor], "1", processAsString), "process1")
    }
  }(system.getDispatcher)
}

