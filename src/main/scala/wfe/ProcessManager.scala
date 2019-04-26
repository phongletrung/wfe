package wfe

import java.io.{ByteArrayInputStream, StringReader}

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import akka.util.Timeout
import javax.xml.stream.XMLInputFactory
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.model.bpmn.instance.Process

import scala.collection.JavaConverters._
import scala.concurrent.duration._
import scala.io.Source

object ProcessManager {
  def parseProcess(process: String) = {
    val reader = new StringReader(process)
    val factory = XMLInputFactory.newInstance()
    val streamReader = factory.createXMLStreamReader(reader)
    val model = Bpmn.readModelFromStream(new ByteArrayInputStream(process.getBytes))
    val process1 = model.getModelElementsByType(model.getModel.getType(classOf[Process])).asScala.head
    var cnt = 0
    process1
  }
  object Processes {

    case class CreateProcess(props: Props, name: String)

  }

  class Processes extends Actor with ActorLogging {

    import Processes._

    var currentProcesses = Set.empty[ActorRef]

    override def receive: Receive = {
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

  def main(args: Array[String]): Unit = {
    implicit val timeout = Timeout(1.seconds)




    val parallelJoin = parseProcess(Source.fromResource("parallelJoin.xml").mkString)
    val parallelWithIntermediate = parseProcess(Source.fromResource("parallelWithIntermediate.xml").mkString)
    val rekursionTest = parseProcess(Source.fromResource("rekursionTest.xml").mkString)
    //    val process4 = parseProcess(Source.fromResource("newtest.xml").mkString)


    val system = ActorSystem("bpmn")
    val processManager = system.actorOf(Props(classOf[Processes]), "processmanager") // /user/processmanager
    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], parallelJoin), "process1")
//        processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], parallelWithIntermediate), "process2")
    //    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], rekursionTest), "process3")
    //    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], newtest), "process4")

    //    Thread.sleep(15000)
    //    system.terminate()

  }
}