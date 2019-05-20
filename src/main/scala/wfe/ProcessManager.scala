package wfe

import java.io.{ByteArrayInputStream, StringReader}

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import javax.xml.stream.XMLInputFactory
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.model.bpmn.instance.{FlowElement, Process}
import scala.concurrent.duration._

import scala.collection.JavaConverters._


object ProcessManager {
  def parseProcess(process: String) : Process = {
    val reader = new StringReader(process)
    val factory = XMLInputFactory.newInstance()
    val streamReader = factory.createXMLStreamReader(reader)
    val model = Bpmn.readModelFromStream(new ByteArrayInputStream(process.getBytes))
    val process1: Process = model.getModelElementsByType(model.getModel.getType(classOf[Process])).asScala.head.asInstanceOf[Process]
    process1
  }


  def getFlowElementById(processS: String, id: String) : FlowElement = {
    val process = parseProcess(processS)
    process.getFlowElements.asScala.filter(element => element.getId.equals(id)).head
  }

  ////    override def preStart = Cluster(context.system).subscribe(self, classOf[ClusterDomainEvent])
  //    val cluster = Cluster(context.system)
  //    // subscribe to cluster changes, re-subscribe when restart
  //    override def preStart(): Unit = {
  //      cluster.subscribe(self, initialStateMode = InitialStateAsEvents, classOf[MemberEvent], classOf[UnreachableMember])
  //    }
  //    override def postStop(): Unit = cluster.unsubscribe(self)
  //
  //
  object Processes {

    case class CreateProcess(props: Props, name: String)

  }

  class Processes extends Actor with ActorLogging {

    import Processes._

//    implicit val system = ActorSystem(clusterName)
//
//    val clusterListener = system.actorOf(Props[ClusterListener], name = "clusterListener")
//
//    sys.addShutdownHook(system.terminate())
//

    var currentProcesses = Set.empty[ActorRef]

    override def receive: Receive = {

      //      case MemberUp(member) => log.info("memberUp={}", member.address)
      //      case event => log.debug("event={}", event.toString)
      case CreateProcess(p, name) =>
        val ref = context.actorOf(p, name)
        currentProcesses += ref
        context watch ref
        ref ! ProcessDefActor.StartProcess()
        println(s"Currently executing ${currentProcesses.size} processes")
      case Terminated(ref) =>
        log.info("Actor terminated....")
        currentProcesses -= ref
    }

        override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(maxNrOfRetries = 20, withinTimeRange = 1 minute) {
          case _: ArithmeticException => Resume
          case _: NullPointerException => Restart
          case _: IllegalArgumentException => Stop
          case _: Exception => Escalate
        }
  }

//  def main(args: Array[String]): Unit = {
//
////    implicit val timeout = Timeout(1.seconds)
//
//
//    val parallelJoin = parseProcess(Source.fromResource("parallelJoin.xml").mkString)
//    val parallelWithIntermediate = parseProcess(Source.fromResource("parallelWithIntermediate.xml").mkString)
//    val rekursionTest = parseProcess(Source.fromResource("rekursionTest.xml").mkString)
//    val stringtest = parseProcess(Source.fromResource("string.xml").mkString)
//    val simpleXOR = parseProcess(Source.fromResource("simpleXOR.xml").mkString)
//    val xortest = parseProcess(Source.fromResource("xortest.xml").mkString)
//    val xorarbitrary = parseProcess(Source.fromResource("xorarbitrary.xml").mkString)
//    val simpleservicetaskonly = parseProcess(Source.fromResource("simpleservicetaskonly.xml").mkString)
//    val newtest = parseProcess(Source.fromResource("xorServiceAndJoin.xml").mkString)
//    val ab = parseProcess(Source.fromResource("ab.xml").mkString)
//
//
//    val system = ActorSystem("bpmn")
//
//
//    val processManager = system.actorOf(Props(classOf[Processes]), "processmanager") // /user/processmanager
//        processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], ab), "process1")
////    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], parallelWithIntermediate), "process2")
//    //    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], rekursionTest), "process3")
////        processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], stringtest), "process4")
////        processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], ab), "process5")
//    //    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], xortest), "process5")
//
//    //    Thread.sleep(15000)
//    //    system.terminate()
//
//  }
}