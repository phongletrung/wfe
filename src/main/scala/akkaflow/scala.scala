package akkaflow

import java.io.StringReader

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import javax.xml.stream.XMLInputFactory

import scala.concurrent.duration._
import org.activiti.bpmn.converter.BpmnXMLConverter

import scala.concurrent.Await
import scala.xml.Elem

object TestDelegate extends Function0[Unit] {
  var executions = 0
  override def apply() {
    executions += 6
  }
}

object HelloWorld {
  def main(args: Array[String]): Unit = {
    implicit val timeout = Timeout(1.seconds)
    def parseProcess(process: Elem) = {
      val definitions = <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                     xmlns:activiti="http://activiti.org/bpmn"
                                     targetNamespace="org.activiti.examples">

        { process }
      </definitions>
      val reader = new StringReader(definitions.toString)
      val factory = XMLInputFactory.newInstance()
      val streamReader = factory.createXMLStreamReader(reader);
      val converter = new BpmnXMLConverter
      val process1 = converter.convertToBpmnModel(streamReader).getProcesses().get(0)
      var cnt = 0
      process1.getFlowElements().forEach(e =>
        if (e.getId == null) {
          e.setId("generatedId" + cnt)
          cnt += 1
        }
      )
//      process1.getFlowElement().forEach(e =>
//      if(e.con)
//      )
      process1
    }
    val input = 1
    val className = TestDelegate.getClass.getName
    val xml =
//      <process id="myProcess" name="My process" isExecutable="true">
//        <startEvent id="startevent1" name="Start"></startEvent>
//        <sequenceFlow id="flow1" sourceRef="startevent1" targetRef="parallelGateway">
//          <conditionExpression xsi:type="tFormalExpression">true</conditionExpression>
//          <conditionExpression xsi:type="tFormalExpression">true</conditionExpression>
//        </sequenceFlow>
//        <sequenceFlow id="flowST1" sourceRef="parallelGateway" targetRef="servicetask1"></sequenceFlow>
//        <sequenceFlow id="flowST2" sourceRef="parallelGateway" targetRef="servicetask2"></sequenceFlow>
//        <parallelGateway id="parallelGateway">
//          <incoming>flow1</incoming>
//          <outgoing>flowST1</outgoing>
//          <outgoing>flowST2</outgoing>
//        </parallelGateway>
//        <serviceTask id="servicetask1" name="Service Task" activiti:class={className}></serviceTask>
//        <serviceTask id="servicetask2" name="Service Task" activiti:class={className}></serviceTask>
//        <sequenceFlow id="flowPG2ST1" sourceRef="servicetask1" targetRef="parallelGateway2"></sequenceFlow>
//        <sequenceFlow id="flowPG2ST2" sourceRef="servicetask2" targetRef="parallelGateway2"></sequenceFlow>
//        <parallelGateway id="parallelGateway2">
//          <incoming>flowPG2ST1</incoming>
//          <incoming>flowPG2ST2</incoming>
//          <outgoing>flowPG2Out</outgoing>
//        </parallelGateway>
//        <sequenceFlow id="flow5" sourceRef="parallelGateway2" targetRef="archive"></sequenceFlow>
//        <serviceTask id="archive" name="Service Task" activiti:class={className}></serviceTask>
//
//        <sequenceFlow id="flowPG2Out" sourceRef="archive" targetRef="endevent1"></sequenceFlow>
//        <endEvent id="endevent1" name="End"></endEvent>
//      </process>


//          <process id="myProcess" name="My process" isExecutable="true">
//            <startEvent id="startevent1" name="Start"></startEvent>
//            <sequenceFlow id="flow1" sourceRef="startevent1" targetRef="parallelGateway">
//              <conditionExpression xsi:type="tFormalExpression">true</conditionExpression>
//              <conditionExpression xsi:type="tFormalExpression">true</conditionExpression>
//            </sequenceFlow>
//            <sequenceFlow id="flowST1" sourceRef="exclusiveGateway" targetRef="servicetask1">
//              <conditionExpression xsi:type="tFormalExpression">${true}</conditionExpression>
//            </sequenceFlow>
//            <sequenceFlow id="flowST2" sourceRef="exclusiveGateway" targetRef="endevent1">
//              <conditionExpression xsi:type="tFormalExpression">${false}</conditionExpression>
//            </sequenceFlow>
//
//            <exclusiveGateway id="exclusiveGateway" name="Exclusive Gateway">
//              <incoming>flow1</incoming>
//              <outgoing>flowST1</outgoing>
//              <outgoing>flowST2</outgoing>/>
//            </exclusiveGateway>
//
//            <endEvent id="endevent1" name="End"></endEvent>
//            <serviceTask id="servicetask1" name="Service Task" activiti:class={className}></serviceTask>
//            <sequenceFlow id="flowPG2ST1" sourceRef="servicetask1" targetRef="endevent2"></sequenceFlow>
//            <endEvent id="endevent2" name="End"></endEvent>
//          </


//      <process id="myProcess" name="My process" isExecutable="true">
//
//        <startEvent id="theStart" />
//        <sequenceFlow id="flow1" sourceRef="theStart" targetRef="fork" />
//        <exclusiveGateway id="fork" />
//        <sequenceFlow sourceRef="fork" targetRef="receivePayment">
//          <conditionExpression xsi:type="tFormalExpression">${{input == 1}}</conditionExpression>
//        </sequenceFlow>
//        <sequenceFlow sourceRef="fork" targetRef="theEnd2">
//          <conditionExpression xsi:type="tFormalExpression">false</conditionExpression>
//        </sequenceFlow>
//      <serviceTask id="receivePayment" name="Receive Payment" activiti:class={className} />
//        <sequenceFlow sourceRef="receivePayment" targetRef="theEnd" />
//                <endEvent id="theEnd" />
//        <endEvent id="theEnd2" />
//
//      <endEvent id="theEnd" />
//      </process>

      <process id="Process_1" isExecutable="true">
        <startEvent id="StartEvent_1">
          <outgoing>SequenceFlow_1sufdm6</outgoing>
        </startEvent>
        <serviceTask id="Task_186tl9n" name="Receive Payment" activiti:class={className} />
        <sequenceFlow id="SequenceFlow_1sufdm6" sourceRef="StartEvent_1" targetRef="Task_186tl9n" />
        <endEvent id="EndEvent_074u7iy">
          <incoming>SequenceFlow_0mym8rz</incoming>
        </endEvent>
        <sequenceFlow id="SequenceFlow_0mym8rz" sourceRef="Task_186tl9n" targetRef="EndEvent_074u7iy" />
      </process>



    val process1 = parseProcess(xml)
    val system = ActorSystem("bpmn")
    val processDefActor = system.actorOf(Props(classOf[ProcessDefActor], process1), name = "process1")
    val processInstanceRefFuture = processDefActor ? ProcessDefActor.StartProcess()
    Await.ready(processInstanceRefFuture, 500.millis)
    Thread.sleep(1000)
    println(TestDelegate.executions)
    system.terminate()

  }
}