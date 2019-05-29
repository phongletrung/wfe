package wfe

import akka.actor._
import akka.pattern.ask
import akka.testkit.TestProbe
import akka.util.Timeout
import org.scalatest.{BeforeAndAfter, FunSpec}
import wfe.ProcessInstanceActor.{ProcessEvent, ProcessFinished, ProcessStarted, StartProcess}
import wfe.ProcessManager.Processes
import wfe.ProcessManager.Processes.CreateProcess

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.io.Source

class ProcessInstanceSpec extends FunSpec with BeforeAndAfter {

  implicit val timeout = Timeout(1.seconds)
  implicit var system: ActorSystem = _

  before {
    system = ActorSystem("bpmn")
  }

  after {
    system.terminate
  }


    val process: String = Source.fromResource("test-process1.xml").mkString

  describe("A process") {
    it("can be started without process variables") {

      val processDefActor = system.actorOf(Props(classOf[Processes]), name = "processManager")
      val myProcessRef = processDefActor ? CreateProcess(Props(classOf[ProcessInstanceActor], "1", process), name="process")
      assert(myProcessRef.isInstanceOf[Future[_]])
    }

    it("sends a ProcessStarted event over the eventstream when it started") {
      val probe1 = TestProbe()
      system.eventStream.subscribe(probe1.ref, classOf[ProcessEvent])

      val processDefActor = system.actorOf(Props(classOf[Processes]), name = "processManager")
      val myProcessRef = processDefActor ? CreateProcess(Props(classOf[ProcessInstanceActor], "1", process), name="process")
      assert(myProcessRef.isInstanceOf[Future[_]])

      val processInstance = probe1.expectMsgPF(500.millis) { case ProcessStarted(processInstance) ⇒ processInstance }
    }

    it("sends a ProcessFinished event over the eventstream when it ended") {
      val probe1 = TestProbe()
      system.eventStream.subscribe(probe1.ref, classOf[ProcessEvent])

      val processDefActor = system.actorOf(Props(classOf[ProcessInstanceActor], "1", process), name = "process")
      val myProcessRef = processDefActor ? StartProcess()
      assert(myProcessRef.isInstanceOf[Future[_]])

      val processInstance1 = probe1.expectMsgPF(500.millis) { case ProcessStarted(processInstance) ⇒ processInstance }
      val processInstance2 = probe1.expectMsgPF(500.millis) { case ProcessFinished(processInstance, _) ⇒ processInstance }
      assert(processInstance1 == processInstance2)
    }

    it("can be started with process variables") {
      val processDefActor = system.actorOf(Props(classOf[ProcessInstanceActor], "1", process), name = "process")
      val myProcessRef = processDefActor ? StartProcess(Map("foo" -> "bar"))
      assert(myProcessRef.isInstanceOf[Future[_]])
    }

    it("sends a ProcessFinished event containing the variables over the eventstream when it ended") {
      val probe1 = TestProbe()
      system.eventStream.subscribe(probe1.ref, classOf[ProcessEvent])

      val processDefActor = system.actorOf(Props(classOf[ProcessInstanceActor], "1", process), name = "process")
      val variables1 = Map("foo" -> "bar")
      val myProcessRef = processDefActor ? StartProcess(variables1)
      assert(myProcessRef.isInstanceOf[Future[_]])

      val processInstance1 = probe1.expectMsgPF(500.millis) { case ProcessStarted(processInstance) ⇒ processInstance }
      val (processInstance2, variables2) = probe1.expectMsgPF(500.millis) { case ProcessFinished(processInstance, variables) ⇒ (processInstance, variables) }
      assert(processInstance1 == processInstance2)
      assert(variables1 == variables2)
    }
  }

//  describe("A process with a wait state") {
//    it("has the process variables with which it is started") {
//      val processDefActor = system.actorOf(Props(classOf[ProcessInstanceActor], "1", processWithWait), name = "process")
//
//      implicit val ec = system.dispatcher
//
//      val variables = Await.result(
//        for (
//          actorRef ← (processDefActor ? StartProcess(Map("foo" -> "bar"))).mapTo[ActorRef];
//          variables ← (actorRef ? GetVariables).mapTo[Map[String, String]]
//        ) yield variables,
//        500.millis)
//
//      assert(variables.get("foo") == Some("bar"))
//    }
//  }
//describe("End to end process engine tests") {
//  it("Parallel Join works") {
//    val parallelJoin = ProcessManager.parseProcess(Source.fromResource("parallelJoin.xml").mkString)
//
//    val system = ActorSystem("bpmn")
//    val processManager = system.actorOf(Props(classOf[Processes]), "processmanager") // /user/processmanager
//    processManager ! ProcessManager.Processes.CreateProcess(Props(classOf[ProcessInstanceActor], "1", parallelJoin), "process1")
//    println(parallelJoin)
//  }
//
//}
}