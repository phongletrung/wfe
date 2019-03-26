package akkaflow

import java.io.{ByteArrayInputStream, StringReader}

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import javax.xml.stream.XMLInputFactory
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.model.bpmn.instance.Process

import scala.collection.JavaConverters._
import scala.concurrent.Await
import scala.concurrent.duration._

object HelloWorld {
  def main(args: Array[String]): Unit = {
    implicit val timeout = Timeout(1.seconds)
    def parseProcess(process: String) = {
      val reader = new StringReader(process)
      val factory = XMLInputFactory.newInstance()
      val streamReader = factory.createXMLStreamReader(reader)
      val model = Bpmn.readModelFromStream(new ByteArrayInputStream(process.getBytes))
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
//
//      <bpmn:process id="Process_1" isExecutable="true">
//        <bpmn:startEvent id="StartEvent_1">
//          <bpmn:outgoing>SequenceFlow_0o1d98c</bpmn:outgoing>
//        </bpmn:startEvent>
//        <bpmn:sequenceFlow id="SequenceFlow_0o1d98c" sourceRef="StartEvent_1" targetRef="Task_0r7h6dm" />
//        <bpmn:serviceTask id="Task_0r7h6dm" name="setze a auf 6" camunda:class="akkaflow.TestDelegate$">
//          <bpmn:incoming>SequenceFlow_0o1d98c</bpmn:incoming>
//          <bpmn:outgoing>SequenceFlow_0kvwd2t</bpmn:outgoing>
//        </bpmn:serviceTask>
//        <bpmn:exclusiveGateway id="ExclusiveGateway_08sxrpr">
//          <bpmn:incoming>SequenceFlow_0kvwd2t</bpmn:incoming>
//          <bpmn:outgoing>SequenceFlow_0skv2n2</bpmn:outgoing>
//          <bpmn:outgoing>SequenceFlow_1ij93hm</bpmn:outgoing>
//        </bpmn:exclusiveGateway>
//        <bpmn:sequenceFlow id="SequenceFlow_0kvwd2t" sourceRef="Task_0r7h6dm" targetRef="ExclusiveGateway_08sxrpr" />
//        <bpmn:sequenceFlow id="SequenceFlow_0skv2n2" sourceRef="ExclusiveGateway_08sxrpr" targetRef="Task_1du1uaw">
//          <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression">${{input &lt; 531}}</bpmn:conditionExpression>
//        </bpmn:sequenceFlow>
//        <bpmn:sequenceFlow id="SequenceFlow_1ij93hm" sourceRef="ExclusiveGateway_08sxrpr" targetRef="Task_0c93ps2">
//          <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression">${{input &lt; 531}}</bpmn:conditionExpression>
//        </bpmn:sequenceFlow>
//        <bpmn:serviceTask id="Task_0c93ps2" name="ausgabe kleiner 6" camunda:class="akkaflow.TestDelegate$">
//          <bpmn:incoming>SequenceFlow_1ij93hm</bpmn:incoming>
//          <bpmn:outgoing>SequenceFlow_0j9956w</bpmn:outgoing>
//        </bpmn:serviceTask>
//        <bpmn:serviceTask id="Task_1du1uaw" name="ausgabe &#62; 6" camunda:class="akkaflow.TestDelegate$">
//          <bpmn:incoming>SequenceFlow_0skv2n2</bpmn:incoming>
//          <bpmn:outgoing>SequenceFlow_1yu8izn</bpmn:outgoing>
//        </bpmn:serviceTask>
//        <bpmn:endEvent id="EndEvent_1ftt523">
//          <bpmn:incoming>SequenceFlow_1yu8izn</bpmn:incoming>
//        </bpmn:endEvent>
//        <bpmn:sequenceFlow id="SequenceFlow_1yu8izn" sourceRef="Task_1du1uaw" targetRef="EndEvent_1ftt523" />
//        <bpmn:endEvent id="EndEvent_0k9bp78">
//          <bpmn:incoming>SequenceFlow_0j9956w</bpmn:incoming>
//        </bpmn:endEvent>
//        <bpmn:sequenceFlow id="SequenceFlow_0j9956w" sourceRef="Task_0c93ps2" targetRef="EndEvent_0k9bp78" />
//      </bpmn:process>
//      """<?xml version="1.0" encoding="UTF-8"?>
//        |<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:camunda="http://camunda.org/schema/1.0/bpmn" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" id="Definitions_1" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="1.9.0">
//        |  <bpmn:process id="Process_1" isExecutable="true">
//        |    <bpmn:startEvent id="StartEvent_1">
//        |      <bpmn:outgoing>SequenceFlow_0o1d98c</bpmn:outgoing>
//        |    </bpmn:startEvent>
//        |    <bpmn:sequenceFlow id="SequenceFlow_0o1d98c" sourceRef="StartEvent_1" targetRef="Task_0r7h6dm" />
//        |    <bpmn:serviceTask id="Task_0r7h6dm" name="setze a auf 6" camunda:class="akkaflow.TestDelegate$">
//        |      <bpmn:incoming>SequenceFlow_0o1d98c</bpmn:incoming>
//        |      <bpmn:outgoing>SequenceFlow_0kvwd2t</bpmn:outgoing>
//        |    </bpmn:serviceTask>
//        |    <bpmn:sequenceFlow id="SequenceFlow_0kvwd2t" sourceRef="Task_0r7h6dm" targetRef="ExclusiveGateway_08sxrpr" />
//        |    <bpmn:sequenceFlow id="SequenceFlow_0skv2n2" sourceRef="ExclusiveGateway_08sxrpr" targetRef="Task_1du1uaw">
//        |      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression"><![CDATA[${input > 5}]]></bpmn:conditionExpression>
//        |    </bpmn:sequenceFlow>
//        |    <bpmn:sequenceFlow id="SequenceFlow_1ij93hm" sourceRef="ExclusiveGateway_08sxrpr" targetRef="Task_0c93ps2">
//        |      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression"><![CDATA[${input < 5}]]></bpmn:conditionExpression>
//        |    </bpmn:sequenceFlow>
//        |    <bpmn:serviceTask id="Task_0c93ps2" name="ausgabe kleiner 6" camunda:class="akkaflow.PrintGreater$">
//        |      <bpmn:incoming>SequenceFlow_1ij93hm</bpmn:incoming>
//        |      <bpmn:outgoing>SequenceFlow_0j9956w</bpmn:outgoing>
//        |    </bpmn:serviceTask>
//        |    <bpmn:serviceTask id="Task_1du1uaw" name="ausgabe &#62; 6" camunda:class="akkaflow.PrintGreater$">
//        |      <bpmn:incoming>SequenceFlow_0skv2n2</bpmn:incoming>
//        |      <bpmn:outgoing>SequenceFlow_1yu8izn</bpmn:outgoing>
//        |    </bpmn:serviceTask>
//        |    <bpmn:endEvent id="EndEvent_1ftt523">
//        |      <bpmn:incoming>SequenceFlow_0onznzq</bpmn:incoming>
//        |    </bpmn:endEvent>
//        |    <bpmn:sequenceFlow id="SequenceFlow_1yu8izn" sourceRef="Task_1du1uaw" targetRef="IntermediateThrowEvent_0vp7meh" />
//        |    <bpmn:endEvent id="EndEvent_0k9bp78">
//        |      <bpmn:incoming>SequenceFlow_0j9956w</bpmn:incoming>
//        |    </bpmn:endEvent>
//        |    <bpmn:sequenceFlow id="SequenceFlow_0j9956w" sourceRef="Task_0c93ps2" targetRef="EndEvent_0k9bp78" />
//        |    <bpmn:sequenceFlow id="SequenceFlow_0onznzq" sourceRef="IntermediateThrowEvent_0vp7meh" targetRef="EndEvent_1ftt523" />
//        |    <bpmn:intermediateCatchEvent id="IntermediateThrowEvent_0vp7meh">
//        |      <bpmn:incoming>SequenceFlow_1yu8izn</bpmn:incoming>
//        |      <bpmn:outgoing>SequenceFlow_0onznzq</bpmn:outgoing>
//        |      <bpmn:timerEventDefinition>
//        |        <bpmn:timeDuration xsi:type="bpmn:tFormalExpression">P10D</bpmn:timeDuration>
//        |      </bpmn:timerEventDefinition>
//        |    </bpmn:intermediateCatchEvent>
//        |    <bpmn:parallelGateway id="ExclusiveGateway_08sxrpr">
//        |      <bpmn:incoming>SequenceFlow_0kvwd2t</bpmn:incoming>
//        |      <bpmn:outgoing>SequenceFlow_0skv2n2</bpmn:outgoing>
//        |      <bpmn:outgoing>SequenceFlow_1ij93hm</bpmn:outgoing>
//        |    </bpmn:parallelGateway>
//        |  </bpmn:process>
//        |  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
//        |    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">
//        |      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">
//        |        <dc:Bounds x="173" y="102" width="36" height="36" />
//        |      </bpmndi:BPMNShape>
//        |      <bpmndi:BPMNEdge id="SequenceFlow_0o1d98c_di" bpmnElement="SequenceFlow_0o1d98c">
//        |        <di:waypoint xsi:type="dc:Point" x="209" y="120" />
//        |        <di:waypoint xsi:type="dc:Point" x="286" y="120" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="247.5" y="98.5" width="0" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNEdge>
//        |      <bpmndi:BPMNShape id="ServiceTask_0o7dmud_di" bpmnElement="Task_0r7h6dm">
//        |        <dc:Bounds x="286" y="80" width="100" height="80" />
//        |      </bpmndi:BPMNShape>
//        |      <bpmndi:BPMNEdge id="SequenceFlow_0kvwd2t_di" bpmnElement="SequenceFlow_0kvwd2t">
//        |        <di:waypoint xsi:type="dc:Point" x="386" y="120" />
//        |        <di:waypoint xsi:type="dc:Point" x="495" y="120" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="395.5" y="98.5" width="90" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNEdge>
//        |      <bpmndi:BPMNEdge id="SequenceFlow_0skv2n2_di" bpmnElement="SequenceFlow_0skv2n2">
//        |        <di:waypoint xsi:type="dc:Point" x="520" y="95" />
//        |        <di:waypoint xsi:type="dc:Point" x="520" y="42" />
//        |        <di:waypoint xsi:type="dc:Point" x="634" y="42" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="490" y="62" width="90" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNEdge>
//        |      <bpmndi:BPMNEdge id="SequenceFlow_1ij93hm_di" bpmnElement="SequenceFlow_1ij93hm">
//        |        <di:waypoint xsi:type="dc:Point" x="520" y="145" />
//        |        <di:waypoint xsi:type="dc:Point" x="520" y="216" />
//        |        <di:waypoint xsi:type="dc:Point" x="614" y="216" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="490" y="174" width="90" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNEdge>
//        |      <bpmndi:BPMNShape id="ServiceTask_1nwiqfo_di" bpmnElement="Task_0c93ps2">
//        |        <dc:Bounds x="614" y="176" width="100" height="80" />
//        |      </bpmndi:BPMNShape>
//        |      <bpmndi:BPMNShape id="ServiceTask_0bqnzfm_di" bpmnElement="Task_1du1uaw">
//        |        <dc:Bounds x="634" y="2" width="100" height="80" />
//        |      </bpmndi:BPMNShape>
//        |      <bpmndi:BPMNShape id="EndEvent_1ftt523_di" bpmnElement="EndEvent_1ftt523">
//        |        <dc:Bounds x="820.9434889434889" y="24" width="36" height="36" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="838.9434889434889" y="63" width="0" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNShape>
//        |      <bpmndi:BPMNEdge id="SequenceFlow_1yu8izn_di" bpmnElement="SequenceFlow_1yu8izn">
//        |        <di:waypoint xsi:type="dc:Point" x="734" y="42" />
//        |        <di:waypoint xsi:type="dc:Point" x="761" y="42" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="747.5" y="20.5" width="0" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNEdge>
//        |      <bpmndi:BPMNShape id="EndEvent_0k9bp78_di" bpmnElement="EndEvent_0k9bp78">
//        |        <dc:Bounds x="805.9434889434889" y="198" width="36" height="36" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="823.9434889434889" y="237" width="0" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNShape>
//        |      <bpmndi:BPMNEdge id="SequenceFlow_0j9956w_di" bpmnElement="SequenceFlow_0j9956w">
//        |        <di:waypoint xsi:type="dc:Point" x="714" y="216" />
//        |        <di:waypoint xsi:type="dc:Point" x="806" y="216" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="760" y="194" width="0" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNEdge>
//        |      <bpmndi:BPMNEdge id="SequenceFlow_0onznzq_di" bpmnElement="SequenceFlow_0onznzq">
//        |        <di:waypoint xsi:type="dc:Point" x="797" y="42" />
//        |        <di:waypoint xsi:type="dc:Point" x="821" y="42" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="809" y="20.5" width="0" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNEdge>
//        |      <bpmndi:BPMNShape id="IntermediateCatchEvent_1uu1d5t_di" bpmnElement="IntermediateThrowEvent_0vp7meh">
//        |        <dc:Bounds x="761" y="24" width="36" height="36" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="778.5669824086604" y="63" width="0" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNShape>
//        |      <bpmndi:BPMNShape id="ParallelGateway_10w9gcq_di" bpmnElement="ExclusiveGateway_08sxrpr">
//        |        <dc:Bounds x="495" y="95" width="50" height="50" />
//        |        <bpmndi:BPMNLabel>
//        |          <dc:Bounds x="475" y="148" width="0" height="13" />
//        |        </bpmndi:BPMNLabel>
//        |      </bpmndi:BPMNShape>
//        |    </bpmndi:BPMNPlane>
//        |  </bpmndi:BPMNDiagram>
//        |</bpmn:definitions>
//        |
//        |"""

      """<?xml version="1.0" encoding="UTF-8"?>
      |<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:camunda="http://camunda.org/schema/1.0/bpmn" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" id="Definitions_1" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="1.9.0">
      |  <bpmn:process id="Process_1" isExecutable="true">
      |    <bpmn:startEvent id="StartEvent_1">
      |      <bpmn:outgoing>SequenceFlow_0o1d98c</bpmn:outgoing>
      |    </bpmn:startEvent>
      |    <bpmn:sequenceFlow id="SequenceFlow_0o1d98c" sourceRef="StartEvent_1" targetRef="Task_0r7h6dm" />
      |    <bpmn:serviceTask id="Task_0r7h6dm" name="setze a auf 6" camunda:class="akkaflow.TestDelegate$">
      |      <bpmn:incoming>SequenceFlow_0o1d98c</bpmn:incoming>
      |      <bpmn:outgoing>SequenceFlow_0kvwd2t</bpmn:outgoing>
      |    </bpmn:serviceTask>
      |    <bpmn:sequenceFlow id="SequenceFlow_0kvwd2t" sourceRef="Task_0r7h6dm" targetRef="ExclusiveGateway_08sxrpr" />
      |    <bpmn:sequenceFlow id="SequenceFlow_0skv2n2" sourceRef="ExclusiveGateway_08sxrpr" targetRef="Task_1du1uaw">
      |      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression"><![CDATA[${input > 5}]]></bpmn:conditionExpression>
      |    </bpmn:sequenceFlow>
      |    <bpmn:sequenceFlow id="SequenceFlow_1ij93hm" sourceRef="ExclusiveGateway_08sxrpr" targetRef="Task_0c93ps2">
      |      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression"><![CDATA[${input < 5}]]></bpmn:conditionExpression>
      |    </bpmn:sequenceFlow>
      |    <bpmn:serviceTask id="Task_0c93ps2" name="ausgabe kleiner 6" camunda:class="akkaflow.PrintGreater$">
      |      <bpmn:incoming>SequenceFlow_1ij93hm</bpmn:incoming>
      |      <bpmn:outgoing>SequenceFlow_0j9956w</bpmn:outgoing>
      |    </bpmn:serviceTask>
      |    <bpmn:serviceTask id="Task_1du1uaw" name="ausgabe &#62; 6" camunda:class="akkaflow.PrintGreater$">
      |      <bpmn:incoming>SequenceFlow_0skv2n2</bpmn:incoming>
      |      <bpmn:outgoing>SequenceFlow_1yu8izn</bpmn:outgoing>
      |    </bpmn:serviceTask>
      |    <bpmn:endEvent id="EndEvent_1ftt523">
      |      <bpmn:incoming>SequenceFlow_0onznzq</bpmn:incoming>
      |    </bpmn:endEvent>
      |    <bpmn:sequenceFlow id="SequenceFlow_1yu8izn" sourceRef="Task_1du1uaw" targetRef="IntermediateThrowEvent_0vp7meh" />
      |    <bpmn:endEvent id="EndEvent_0k9bp78">
      |      <bpmn:incoming>SequenceFlow_0j9956w</bpmn:incoming>
      |    </bpmn:endEvent>
      |    <bpmn:sequenceFlow id="SequenceFlow_0j9956w" sourceRef="Task_0c93ps2" targetRef="EndEvent_0k9bp78" />
      |    <bpmn:sequenceFlow id="SequenceFlow_0onznzq" sourceRef="IntermediateThrowEvent_0vp7meh" targetRef="EndEvent_1ftt523" />
      |    <bpmn:intermediateCatchEvent id="IntermediateThrowEvent_0vp7meh">
      |      <bpmn:incoming>SequenceFlow_1yu8izn</bpmn:incoming>
      |      <bpmn:outgoing>SequenceFlow_0onznzq</bpmn:outgoing>
      |      <bpmn:timerEventDefinition>
      |        <bpmn:timeDuration xsi:type="bpmn:tFormalExpression">P10D</bpmn:timeDuration>
      |      </bpmn:timerEventDefinition>
      |    </bpmn:intermediateCatchEvent>
      |    <bpmn:parallelGateway id="ExclusiveGateway_08sxrpr">
      |      <bpmn:incoming>SequenceFlow_0kvwd2t</bpmn:incoming>
      |      <bpmn:outgoing>SequenceFlow_0skv2n2</bpmn:outgoing>
      |      <bpmn:outgoing>SequenceFlow_1ij93hm</bpmn:outgoing>
      |    </bpmn:parallelGateway>
      |  </bpmn:process>
      |  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
      |    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">
      |      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">
      |        <dc:Bounds x="173" y="102" width="36" height="36" />
      |      </bpmndi:BPMNShape>
      |      <bpmndi:BPMNEdge id="SequenceFlow_0o1d98c_di" bpmnElement="SequenceFlow_0o1d98c">
      |        <di:waypoint xsi:type="dc:Point" x="209" y="120" />
      |        <di:waypoint xsi:type="dc:Point" x="286" y="120" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="247.5" y="98.5" width="0" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNEdge>
      |      <bpmndi:BPMNShape id="ServiceTask_0o7dmud_di" bpmnElement="Task_0r7h6dm">
      |        <dc:Bounds x="286" y="80" width="100" height="80" />
      |      </bpmndi:BPMNShape>
      |      <bpmndi:BPMNEdge id="SequenceFlow_0kvwd2t_di" bpmnElement="SequenceFlow_0kvwd2t">
      |        <di:waypoint xsi:type="dc:Point" x="386" y="120" />
      |        <di:waypoint xsi:type="dc:Point" x="495" y="120" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="395.5" y="98.5" width="90" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNEdge>
      |      <bpmndi:BPMNEdge id="SequenceFlow_0skv2n2_di" bpmnElement="SequenceFlow_0skv2n2">
      |        <di:waypoint xsi:type="dc:Point" x="520" y="95" />
      |        <di:waypoint xsi:type="dc:Point" x="520" y="42" />
      |        <di:waypoint xsi:type="dc:Point" x="634" y="42" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="490" y="62" width="90" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNEdge>
      |      <bpmndi:BPMNEdge id="SequenceFlow_1ij93hm_di" bpmnElement="SequenceFlow_1ij93hm">
      |        <di:waypoint xsi:type="dc:Point" x="520" y="145" />
      |        <di:waypoint xsi:type="dc:Point" x="520" y="216" />
      |        <di:waypoint xsi:type="dc:Point" x="614" y="216" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="490" y="174" width="90" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNEdge>
      |      <bpmndi:BPMNShape id="ServiceTask_1nwiqfo_di" bpmnElement="Task_0c93ps2">
      |        <dc:Bounds x="614" y="176" width="100" height="80" />
      |      </bpmndi:BPMNShape>
      |      <bpmndi:BPMNShape id="ServiceTask_0bqnzfm_di" bpmnElement="Task_1du1uaw">
      |        <dc:Bounds x="634" y="2" width="100" height="80" />
      |      </bpmndi:BPMNShape>
      |      <bpmndi:BPMNShape id="EndEvent_1ftt523_di" bpmnElement="EndEvent_1ftt523">
      |        <dc:Bounds x="820.9434889434889" y="24" width="36" height="36" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="838.9434889434889" y="63" width="0" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNShape>
      |      <bpmndi:BPMNEdge id="SequenceFlow_1yu8izn_di" bpmnElement="SequenceFlow_1yu8izn">
      |        <di:waypoint xsi:type="dc:Point" x="734" y="42" />
      |        <di:waypoint xsi:type="dc:Point" x="761" y="42" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="747.5" y="20.5" width="0" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNEdge>
      |      <bpmndi:BPMNShape id="EndEvent_0k9bp78_di" bpmnElement="EndEvent_0k9bp78">
      |        <dc:Bounds x="805.9434889434889" y="198" width="36" height="36" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="823.9434889434889" y="237" width="0" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNShape>
      |      <bpmndi:BPMNEdge id="SequenceFlow_0j9956w_di" bpmnElement="SequenceFlow_0j9956w">
      |        <di:waypoint xsi:type="dc:Point" x="714" y="216" />
      |        <di:waypoint xsi:type="dc:Point" x="806" y="216" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="760" y="194" width="0" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNEdge>
      |      <bpmndi:BPMNEdge id="SequenceFlow_0onznzq_di" bpmnElement="SequenceFlow_0onznzq">
      |        <di:waypoint xsi:type="dc:Point" x="797" y="42" />
      |        <di:waypoint xsi:type="dc:Point" x="821" y="42" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="809" y="20.5" width="0" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNEdge>
      |      <bpmndi:BPMNShape id="IntermediateCatchEvent_1uu1d5t_di" bpmnElement="IntermediateThrowEvent_0vp7meh">
      |        <dc:Bounds x="761" y="24" width="36" height="36" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="778.5669824086604" y="63" width="0" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNShape>
      |      <bpmndi:BPMNShape id="ParallelGateway_10w9gcq_di" bpmnElement="ExclusiveGateway_08sxrpr">
      |        <dc:Bounds x="495" y="95" width="50" height="50" />
      |        <bpmndi:BPMNLabel>
      |          <dc:Bounds x="475" y="148" width="0" height="13" />
      |        </bpmndi:BPMNLabel>
      |      </bpmndi:BPMNShape>
      |    </bpmndi:BPMNPlane>
      |  </bpmndi:BPMNDiagram>
      |</bpmn:definitions>
      |
    """



    val process1 = parseProcess(xml.stripMargin)
    val system = ActorSystem("bpmn")
    val processDefActor = system.actorOf(Props(classOf[ProcessDefActor], process1), name = "process1")
    val processInstanceRefFuture = processDefActor ? ProcessDefActor.StartProcess()
    Await.ready(processInstanceRefFuture, 500.millis)
//    Thread.sleep(1000)
    println(TestDelegate.executions)
//    Thread.sleep(15000)
//    system.terminate()

  }
}