package wfe.flownodes

import akka.actor._
import akka.pattern.ask
import akka.testkit.TestKit
import akka.util.Timeout
import wfe.ProcessDefActor
import org.scalatest.{BeforeAndAfter, FunSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object TestDelegate extends Function0[Unit] {
  var executions = 0
  override def apply() {
    executions += 1
  }
}

class ServiceTaskSpec(_system: ActorSystem) extends TestKit(_system) with FunSpecLike with BeforeAndAfter {
  implicit val timeout = Timeout(1.seconds)

  def this() = this(ActorSystem("bpmn"))

  describe("A process with a service task") {

    val className = TestDelegate.getClass.getName()
    val xml =
      <process id="myProcess" name="My process" isExecutable="true">
        <startEvent id="startevent1" name="Start"></startEvent>
        <sequenceFlow id="flow1" sourceRef="startevent1" targetRef="servicetask1"></sequenceFlow>
        <serviceTask id="servicetask1" name="Service Task" activiti:class={className}></serviceTask>
        <sequenceFlow id="flow2" sourceRef="servicetask1" targetRef="endevent1"></sequenceFlow>
 		<endEvent id="endevent1" name="End"></endEvent>
      </process>

    it("executes the service task") {
      val processDefActor = system.actorOf(Props(classOf[ProcessDefActor], xml.mkString), name = "process1")
      val processInstanceRefFuture = processDefActor ? ProcessDefActor.StartProcess()
      Await.ready(processInstanceRefFuture, 500.millis)

      awaitAssert(assert(TestDelegate.executions === 1))
    }

  }
}