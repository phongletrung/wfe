package wfe

import akka.actor.{ActorSystem, Props}
import wfe.ProcessManager.{Processes, parseProcess}

import scala.io.Source

object ClusterApp extends App {
  import ClusteringConfig._

  val system = ActorSystem(clusterName)
  system.actorOf(Props[ClusterListener])

  val parallelJoin = parseProcess(Source.fromResource("parallelJoin.xml").mkString)

  val processManager = system.actorOf(Props(classOf[Processes]), "processmanager")
  processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], parallelJoin), "process1")

  sys.addShutdownHook(system.terminate())

}

