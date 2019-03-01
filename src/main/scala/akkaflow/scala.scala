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
      val definitions = <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:activiti="http://activiti.org/bpmn" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" typeLanguage="http://www.w3.org/2001/XMLSchema" expressionLanguage="http://www.w3.org/1999/XPath" targetNamespace="http://www.activiti.org/test">
        { process }
      </definitions>
      val reader = new StringReader(definitions.toString)
      val factory = XMLInputFactory.newInstance()
      val streamReader = factory.createXMLStreamReader(reader);
      val converter = new BpmnXMLConverter
      converter.convertToBpmnModel(streamReader).getProcesses().get(0)
    }
    val className = TestDelegate.getClass.getName
    val xml =
      <process id="myProcess" name="My process" isExecutable="true">
        <startEvent id="startevent1" name="Start"></startEvent>
        <sequenceFlow id="flow1" sourceRef="startevent1" targetRef="parallelGateway">
          <conditionExpression xsi:type="tFormalExpression">true</conditionExpression>
          <conditionExpression xsi:type="tFormalExpression">true</conditionExpression>
        </sequenceFlow>
        <sequenceFlow id="flowST1" sourceRef="parallelGateway" targetRef="servicetask1"></sequenceFlow>
        <sequenceFlow id="flowST2" sourceRef="parallelGateway" targetRef="servicetask2"></sequenceFlow>
        <parallelGateway id="parallelGateway">
          <incoming>flow1</incoming>
          <outgoing>flowST1</outgoing>
          <outgoing>flowST2</outgoing>
        </parallelGateway>
        <serviceTask id="servicetask1" name="Service Task" activiti:class={className}></serviceTask>
        <serviceTask id="servicetask2" name="Service Task" activiti:class={className}></serviceTask>
        <sequenceFlow id="flowPG2ST1" sourceRef="servicetask1" targetRef="parallelGateway2"></sequenceFlow>
        <sequenceFlow id="flowPG2ST2" sourceRef="servicetask2" targetRef="parallelGateway2"></sequenceFlow>
        <parallelGateway id="parallelGateway2">
          <incoming>flowPG2ST1</incoming>
          <incoming>flowPG2ST2</incoming>
          <outgoing>flowPG2Out</outgoing>
        </parallelGateway>
        <sequenceFlow id="flowPG2Out" sourceRef="parallelGateway2" targetRef="endevent1"></sequenceFlow>
        <endEvent id="endevent1" name="End"></endEvent>
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