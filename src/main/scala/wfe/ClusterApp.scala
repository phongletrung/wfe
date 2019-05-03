package wfe

import akka.actor.{ActorSystem, Props}
import wfe.ProcessManager._

import scala.io.Source

object ClusterApp extends App {
  import ClusteringConfig._

  implicit val system = ActorSystem(clusterName)

  val clusterListener = system.actorOf(Props[ClusterListener], name = "clusterListener")

  sys.addShutdownHook(system.terminate())

//  val system = ActorSystem(clusterName)
//  system.actorOf(Props[ClusterListener])
//
  val parallelJoin = parseProcess(Source.fromResource("string.xml").mkString)

  val processManager = system.actorOf(Props(classOf[Processes]), "processmanager")
  processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], parallelJoin), "process1")
//
//  sys.addShutdownHook(system.terminate())

}

