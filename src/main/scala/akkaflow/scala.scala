package akkaflow

import java.io.{ByteArrayInputStream, StringReader}

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import javax.xml.stream.XMLInputFactory
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.model.bpmn.instance.Process

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.xml.Elem

import scala.collection.JavaConverters._


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
                                     xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL"
                                     xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"
                                     xmlns:di="http://www.omg.org/spec/DD/20100524/DI"
                                     xmlns:dc="http://www.omg.org/spec/DD/20100524/DC"
                                     xmlns:camunda="http://camunda.org/schema/1.0/bpmn"
                                     targetNamespace="http://camunda.org/examples">

        { process }
      </definitions>
      val reader = new StringReader(definitions.toString)
      val factory = XMLInputFactory.newInstance()
      val streamReader = factory.createXMLStreamReader(reader)
      val model = Bpmn.readModelFromStream(new ByteArrayInputStream(definitions.toString().getBytes))
      val process1 = model.getModelElementsByType(model.getModel.getType(classOf[Process])).asScala.head
      var cnt = 0
//      process1.getFlowElement().forEach(e =>
//        if (e.getId == null) {
//          e.setId("generatedId" + cnt)
//          cnt += 1
//        }
//      )
//      process1.getFlowElement().forEach(e =>
//      if(e.con)
//      )
      process1
    }
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

      <bpmn:process id="Process_1u2b75f" isExecutable="false">
        <bpmn:startEvent id="StartEvent_0y6xjv6">
          <bpmn:outgoing>SequenceFlow_0crmfdo</bpmn:outgoing>
        </bpmn:startEvent>
        <bpmn:sequenceFlow id="SequenceFlow_0crmfdo" sourceRef="StartEvent_0y6xjv6" targetRef="ExclusiveGateway_124rq37" />
        <bpmn:sequenceFlow id="SequenceFlow_0jpl7bb" name="" sourceRef="ExclusiveGateway_124rq37" targetRef="Task_0bz8cbw">
          <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression">${{input == 530}}</bpmn:conditionExpression>
        </bpmn:sequenceFlow>
        <bpmn:endEvent id="EndEvent_1f7gwmo">
          <bpmn:incoming>SequenceFlow_0t8f98m</bpmn:incoming>
        </bpmn:endEvent>
        <bpmn:sequenceFlow id="SequenceFlow_0t8f98m" sourceRef="Task_0bz8cbw" targetRef="EndEvent_1f7gwmo" />
        <bpmn:serviceTask id="Task_0bz8cbw" camunda:class="akkaflow.TestDelegate$">
          <bpmn:incoming>SequenceFlow_0jpl7bb</bpmn:incoming>
          <bpmn:outgoing>SequenceFlow_0t8f98m</bpmn:outgoing>
        </bpmn:serviceTask>
        <bpmn:serviceTask id="Task_0qhu2ur" camunda:class="akkaflow.TestDelegate$">
          <bpmn:incoming>SequenceFlow_1ieubgb</bpmn:incoming>
          <bpmn:outgoing>SequenceFlow_1alq3jf</bpmn:outgoing>
        </bpmn:serviceTask>
        <bpmn:sequenceFlow id="SequenceFlow_1ieubgb" sourceRef="ExclusiveGateway_124rq37" targetRef="Task_0qhu2ur">
          <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression">${{input &lt; 531}}</bpmn:conditionExpression>
        </bpmn:sequenceFlow>
        <bpmn:endEvent id="EndEvent_1inpzfr">
          <bpmn:incoming>SequenceFlow_1alq3jf</bpmn:incoming>
        </bpmn:endEvent>
        <bpmn:sequenceFlow id="SequenceFlow_1alq3jf" sourceRef="Task_0qhu2ur" targetRef="EndEvent_1inpzfr" />
        <bpmn:inclusiveGateway id="ExclusiveGateway_124rq37">
          <bpmn:incoming>SequenceFlow_0crmfdo</bpmn:incoming>
          <bpmn:outgoing>SequenceFlow_0jpl7bb</bpmn:outgoing>
          <bpmn:outgoing>SequenceFlow_1ieubgb</bpmn:outgoing>
        </bpmn:inclusiveGateway>
      </bpmn:process>

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