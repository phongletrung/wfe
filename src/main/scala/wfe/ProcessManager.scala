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

object ProcessManager {
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
    val xml =
//
      """<?xml version="1.0" encoding="UTF-8"?>
        |<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:camunda="http://camunda.org/schema/1.0/bpmn" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" id="Definitions_1" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="1.9.0">
        |  <bpmn:process id="Process_1" isExecutable="true">
        |    <bpmn:startEvent id="StartEvent_1">
        |      <bpmn:outgoing>SequenceFlow_0o1d98c</bpmn:outgoing>
        |    </bpmn:startEvent>
        |    <bpmn:sequenceFlow id="SequenceFlow_0o1d98c" sourceRef="StartEvent_1" targetRef="Task_0r7h6dm" />
        |    <bpmn:serviceTask id="Task_0r7h6dm" name="setze a auf 6" camunda:class="wfe.TestDelegate$">
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
        |    <bpmn:serviceTask id="Task_0c93ps2" name="ausgabe kleiner 6" camunda:class="wfe.PrintGreater$">
        |      <bpmn:incoming>SequenceFlow_1ij93hm</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_0j9956w</bpmn:outgoing>
        |    </bpmn:serviceTask>
        |    <bpmn:serviceTask id="Task_1du1uaw" name="ausgabe &#62; 6" camunda:class="wfe.PrintGreater$">
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
        |"""

      """<?xml version="1.0" encoding="UTF-8"?>
        |<bpmn:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:camunda="http://camunda.org/schema/1.0/bpmn" id="Definitions_19p0gnh" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="1.9.0">
        |  <bpmn:process id="Process_016j67s" isExecutable="false">
        |    <bpmn:startEvent id="StartEvent_1ngc0os">
        |      <bpmn:outgoing>SequenceFlow_0yj8ev4</bpmn:outgoing>
        |    </bpmn:startEvent>
        |    <bpmn:sequenceFlow id="SequenceFlow_1yoj1sk" name="input &#60;5" sourceRef="ExclusiveGateway_06rg6sv" targetRef="Task_1snghs1">
        |      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression"><![CDATA[${input > 5}]]></bpmn:conditionExpression>
        |    </bpmn:sequenceFlow>
        |    <bpmn:serviceTask id="Task_1wmikxr" camunda:class="wfe.TestDelegate$">
        |      <bpmn:incoming>SequenceFlow_0yj8ev4</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_0rjxnli</bpmn:outgoing>
        |    </bpmn:serviceTask>
        |    <bpmn:sequenceFlow id="SequenceFlow_0yj8ev4" sourceRef="StartEvent_1ngc0os" targetRef="Task_1wmikxr" />
        |    <bpmn:sequenceFlow id="SequenceFlow_0rjxnli" sourceRef="Task_1wmikxr" targetRef="ExclusiveGateway_06rg6sv" />
        |    <bpmn:serviceTask id="Task_1snghs1" camunda:class="wfe.PrintGreater$">
        |      <bpmn:incoming>SequenceFlow_1yoj1sk</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_0otpucv</bpmn:outgoing>
        |    </bpmn:serviceTask>
        |    <bpmn:sequenceFlow id="SequenceFlow_1kffwc6" sourceRef="ExclusiveGateway_06rg6sv" targetRef="Task_0torzi8">
        |      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression"><![CDATA[${input > 5}]]></bpmn:conditionExpression>
        |    </bpmn:sequenceFlow>
        |    <bpmn:serviceTask id="Task_0torzi8" camunda:class="wfe.PrintGreater$">
        |      <bpmn:incoming>SequenceFlow_1kffwc6</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_1stdkye</bpmn:outgoing>
        |    </bpmn:serviceTask>
        |    <bpmn:exclusiveGateway id="ExclusiveGateway_06rg6sv">
        |      <bpmn:incoming>SequenceFlow_0rjxnli</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_1yoj1sk</bpmn:outgoing>
        |      <bpmn:outgoing>SequenceFlow_1kffwc6</bpmn:outgoing>
        |    </bpmn:exclusiveGateway>
        |    <bpmn:exclusiveGateway id="ExclusiveGateway_0c5iohn">
        |      <bpmn:incoming>SequenceFlow_1stdkye</bpmn:incoming>
        |      <bpmn:incoming>SequenceFlow_0otpucv</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_1yo5itv</bpmn:outgoing>
        |    </bpmn:exclusiveGateway>
        |    <bpmn:sequenceFlow id="SequenceFlow_1stdkye" sourceRef="Task_0torzi8" targetRef="ExclusiveGateway_0c5iohn" />
        |    <bpmn:sequenceFlow id="SequenceFlow_0otpucv" sourceRef="Task_1snghs1" targetRef="ExclusiveGateway_0c5iohn" />
        |    <bpmn:endEvent id="EndEvent_1i0l6v0">
        |      <bpmn:incoming>SequenceFlow_1yo5itv</bpmn:incoming>
        |    </bpmn:endEvent>
        |    <bpmn:sequenceFlow id="SequenceFlow_1yo5itv" sourceRef="ExclusiveGateway_0c5iohn" targetRef="EndEvent_1i0l6v0">
        |      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression"><![CDATA[${input > 5}]]></bpmn:conditionExpression>
        |    </bpmn:sequenceFlow>
        |  </bpmn:process>
        |  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
        |    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_016j67s">
        |      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1ngc0os">
        |        <dc:Bounds x="156" y="81" width="36" height="36" />
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_1yoj1sk_di" bpmnElement="SequenceFlow_1yoj1sk">
        |        <di:waypoint xsi:type="dc:Point" x="292" y="99" />
        |        <di:waypoint xsi:type="dc:Point" x="342" y="99" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="154" y="21" width="40" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNShape id="ServiceTask_0476t19_di" bpmnElement="Task_1wmikxr">
        |        <dc:Bounds x="242" y="174" width="100" height="80" />
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_0yj8ev4_di" bpmnElement="SequenceFlow_0yj8ev4">
        |        <di:waypoint xsi:type="dc:Point" x="192" y="99" />
        |        <di:waypoint xsi:type="dc:Point" x="217" y="99" />
        |        <di:waypoint xsi:type="dc:Point" x="217" y="214" />
        |        <di:waypoint xsi:type="dc:Point" x="242" y="214" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="232" y="149.5" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNEdge id="SequenceFlow_0rjxnli_di" bpmnElement="SequenceFlow_0rjxnli">
        |        <di:waypoint xsi:type="dc:Point" x="292" y="174" />
        |        <di:waypoint xsi:type="dc:Point" x="292" y="149" />
        |        <di:waypoint xsi:type="dc:Point" x="267" y="149" />
        |        <di:waypoint xsi:type="dc:Point" x="267" y="124" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="279.5" y="127.5" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNShape id="ServiceTask_1ym7zba_di" bpmnElement="Task_1snghs1">
        |        <dc:Bounds x="342" y="59" width="100" height="80" />
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_1kffwc6_di" bpmnElement="SequenceFlow_1kffwc6">
        |        <di:waypoint xsi:type="dc:Point" x="267" y="74" />
        |        <di:waypoint xsi:type="dc:Point" x="267" y="52" />
        |        <di:waypoint xsi:type="dc:Point" x="324" y="52" />
        |        <di:waypoint xsi:type="dc:Point" x="324" y="30" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="295.5" y="30.5" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNShape id="ServiceTask_18ire16_di" bpmnElement="Task_0torzi8">
        |        <dc:Bounds x="275" y="-50" width="100" height="80" />
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNShape id="ExclusiveGateway_0k7hqhw_di" bpmnElement="ExclusiveGateway_06rg6sv" isMarkerVisible="true">
        |        <dc:Bounds x="242" y="74" width="50" height="50" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="222" y="124" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNShape id="ExclusiveGateway_0c5iohn_di" bpmnElement="ExclusiveGateway_0c5iohn" isMarkerVisible="true">
        |        <dc:Bounds x="462.08986175115206" y="-17.320852534562206" width="50" height="50" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="487.08986175115206" y="35.679147465437794" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_1stdkye_di" bpmnElement="SequenceFlow_1stdkye">
        |        <di:waypoint xsi:type="dc:Point" x="375" y="-10" />
        |        <di:waypoint xsi:type="dc:Point" x="419" y="-10" />
        |        <di:waypoint xsi:type="dc:Point" x="419" y="8" />
        |        <di:waypoint xsi:type="dc:Point" x="462" y="8" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="434" y="-8" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNEdge id="SequenceFlow_0otpucv_di" bpmnElement="SequenceFlow_0otpucv">
        |        <di:waypoint xsi:type="dc:Point" x="442" y="99" />
        |        <di:waypoint xsi:type="dc:Point" x="487" y="99" />
        |        <di:waypoint xsi:type="dc:Point" x="487" y="33" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="464.5" y="77" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNShape id="EndEvent_1i0l6v0_di" bpmnElement="EndEvent_1i0l6v0">
        |        <dc:Bounds x="535.0898617511521" y="-10" width="36" height="36" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="553.0898617511521" y="29" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_1yo5itv_di" bpmnElement="SequenceFlow_1yo5itv">
        |        <di:waypoint xsi:type="dc:Point" x="512" y="8" />
        |        <di:waypoint xsi:type="dc:Point" x="535" y="8" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="523.5" y="-14" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |    </bpmndi:BPMNPlane>
        |  </bpmndi:BPMNDiagram>
        |</bpmn:definitions>
        |
    """

    val xml2 =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:camunda="http://camunda.org/schema/1.0/bpmn" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" id="Definitions_1" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="1.9.0">
        |  <bpmn:process id="Process_1" isExecutable="true">
        |    <bpmn:startEvent id="StartEvent_1gfqk7p">
        |      <bpmn:outgoing>SequenceFlow_1mplmz2</bpmn:outgoing>
        |    </bpmn:startEvent>
        |    <bpmn:serviceTask id="ServiceTask_1hg31a2" name="setze a auf 6" camunda:class="wfe.TestDelegate2$">
        |      <bpmn:incoming>SequenceFlow_1mplmz2</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_1725yso</bpmn:outgoing>
        |    </bpmn:serviceTask>
        |    <bpmn:sequenceFlow id="SequenceFlow_1mplmz2" sourceRef="StartEvent_1gfqk7p" targetRef="ServiceTask_1hg31a2" />
        |    <bpmn:endEvent id="EndEvent_19jpiyz">
        |      <bpmn:incoming>SequenceFlow_1725yso</bpmn:incoming>
        |    </bpmn:endEvent>
        |    <bpmn:sequenceFlow id="SequenceFlow_1725yso" sourceRef="ServiceTask_1hg31a2" targetRef="EndEvent_19jpiyz" />
        |  </bpmn:process>
        |  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
        |    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">
        |      <bpmndi:BPMNShape id="StartEvent_1gfqk7p_di" bpmnElement="StartEvent_1gfqk7p">
        |        <dc:Bounds x="510" y="296" width="36" height="36" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="483" y="332" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNShape id="ServiceTask_1hg31a2_di" bpmnElement="ServiceTask_1hg31a2">
        |        <dc:Bounds x="623" y="274" width="100" height="80" />
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_1mplmz2_di" bpmnElement="SequenceFlow_1mplmz2">
        |        <di:waypoint xsi:type="dc:Point" x="546" y="314" />
        |        <di:waypoint xsi:type="dc:Point" x="623" y="314" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="540.5" y="293" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNShape id="EndEvent_19jpiyz_di" bpmnElement="EndEvent_19jpiyz">
        |        <dc:Bounds x="784" y="296" width="36" height="36" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="802" y="335" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_1725yso_di" bpmnElement="SequenceFlow_1725yso">
        |        <di:waypoint xsi:type="dc:Point" x="723" y="314" />
        |        <di:waypoint xsi:type="dc:Point" x="784" y="314" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="753.5" y="292" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |    </bpmndi:BPMNPlane>
        |  </bpmndi:BPMNDiagram>
        |</bpmn:definitions>
        |
        |"""


    val xml3 =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<bpmn:definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:camunda="http://camunda.org/schema/1.0/bpmn" id="Definitions_1" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="1.9.0">
        |  <bpmn:process id="Process_1" isExecutable="true">
        |    <bpmn:startEvent id="StartEvent_1">
        |      <bpmn:outgoing>SequenceFlow_1jpnr2m</bpmn:outgoing>
        |    </bpmn:startEvent>
        |    <bpmn:sequenceFlow id="SequenceFlow_1jpnr2m" sourceRef="StartEvent_1" targetRef="Task_0wzqa3i" />
        |    <bpmn:exclusiveGateway id="ExclusiveGateway_0uo9ui8">
        |      <bpmn:incoming>SequenceFlow_1e0oe04</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_01hax3h</bpmn:outgoing>
        |      <bpmn:outgoing>SequenceFlow_18f313p</bpmn:outgoing>
        |    </bpmn:exclusiveGateway>
        |    <bpmn:sequenceFlow id="SequenceFlow_1e0oe04" sourceRef="Task_0wzqa3i" targetRef="ExclusiveGateway_0uo9ui8" />
        |    <bpmn:sequenceFlow id="SequenceFlow_01hax3h" sourceRef="ExclusiveGateway_0uo9ui8" targetRef="Task_07ip4qh">
        |      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression"><![CDATA[{a > 8}$]]></bpmn:conditionExpression>
        |    </bpmn:sequenceFlow>
        |    <bpmn:endEvent id="EndEvent_1koms4n">
        |      <bpmn:incoming>SequenceFlow_1y8sq1o</bpmn:incoming>
        |    </bpmn:endEvent>
        |    <bpmn:sequenceFlow id="SequenceFlow_1y8sq1o" sourceRef="Task_07ip4qh" targetRef="EndEvent_1koms4n" />
        |    <bpmn:serviceTask id="Task_0wzqa3i" name="IncreaseByEight" camunda:class="wfe,IncreaseByEight$">
        |      <bpmn:incoming>SequenceFlow_1jpnr2m</bpmn:incoming>
        |      <bpmn:incoming>SequenceFlow_18f313p</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_1e0oe04</bpmn:outgoing>
        |    </bpmn:serviceTask>
        |    <bpmn:serviceTask id="Task_07ip4qh" camunda:class="wfe.MultiplyWithTen$">
        |      <bpmn:incoming>SequenceFlow_01hax3h</bpmn:incoming>
        |      <bpmn:outgoing>SequenceFlow_1y8sq1o</bpmn:outgoing>
        |    </bpmn:serviceTask>
        |    <bpmn:sequenceFlow id="SequenceFlow_18f313p" sourceRef="ExclusiveGateway_0uo9ui8" targetRef="Task_0wzqa3i">
        |      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression"><![CDATA[{a < 8}$]]></bpmn:conditionExpression>
        |    </bpmn:sequenceFlow>
        |  </bpmn:process>
        |  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
        |    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">
        |      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">
        |        <dc:Bounds x="154" y="242" width="36" height="36" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="127" y="278" width="90" height="20" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_1jpnr2m_di" bpmnElement="SequenceFlow_1jpnr2m">
        |        <di:waypoint xsi:type="dc:Point" x="190" y="260" />
        |        <di:waypoint xsi:type="dc:Point" x="249" y="260" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="219.5" y="238.5" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNShape id="ExclusiveGateway_0uo9ui8_di" bpmnElement="ExclusiveGateway_0uo9ui8" isMarkerVisible="true">
        |        <dc:Bounds x="406" y="235" width="50" height="50" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="431" y="288" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_1e0oe04_di" bpmnElement="SequenceFlow_1e0oe04">
        |        <di:waypoint xsi:type="dc:Point" x="349" y="260" />
        |        <di:waypoint xsi:type="dc:Point" x="406" y="260" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="377.5" y="238.5" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNEdge id="SequenceFlow_01hax3h_di" bpmnElement="SequenceFlow_01hax3h">
        |        <di:waypoint xsi:type="dc:Point" x="456" y="260" />
        |        <di:waypoint xsi:type="dc:Point" x="499" y="260" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="477.5" y="238.5" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNShape id="EndEvent_1koms4n_di" bpmnElement="EndEvent_1koms4n">
        |        <dc:Bounds x="647" y="251" width="36" height="36" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="665" y="290" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_1y8sq1o_di" bpmnElement="SequenceFlow_1y8sq1o">
        |        <di:waypoint xsi:type="dc:Point" x="599" y="260" />
        |        <di:waypoint xsi:type="dc:Point" x="623" y="260" />
        |        <di:waypoint xsi:type="dc:Point" x="623" y="269" />
        |        <di:waypoint xsi:type="dc:Point" x="647" y="269" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="638" y="258" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |      <bpmndi:BPMNShape id="ServiceTask_1vqvtuh_di" bpmnElement="Task_0wzqa3i">
        |        <dc:Bounds x="249" y="220" width="100" height="80" />
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNShape id="ServiceTask_1fxed7b_di" bpmnElement="Task_07ip4qh">
        |        <dc:Bounds x="499" y="220" width="100" height="80" />
        |      </bpmndi:BPMNShape>
        |      <bpmndi:BPMNEdge id="SequenceFlow_18f313p_di" bpmnElement="SequenceFlow_18f313p">
        |        <di:waypoint xsi:type="dc:Point" x="431" y="285" />
        |        <di:waypoint xsi:type="dc:Point" x="431" y="431" />
        |        <di:waypoint xsi:type="dc:Point" x="299" y="431" />
        |        <di:waypoint xsi:type="dc:Point" x="299" y="300" />
        |        <bpmndi:BPMNLabel>
        |          <dc:Bounds x="365" y="409.5" width="0" height="13" />
        |        </bpmndi:BPMNLabel>
        |      </bpmndi:BPMNEdge>
        |    </bpmndi:BPMNPlane>
        |  </bpmndi:BPMNDiagram>
        |</bpmn:definitions>
        ||"""

    val xml4 =

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

      override def supervisorStrategy: SupervisorStrategy =   OneForOneStrategy(maxNrOfRetries = 20, withinTimeRange = 1 minute) {
        case _: ArithmeticException      => Resume
        case _: NullPointerException     => Restart
        case _: IllegalArgumentException => Stop
        case _: Exception                => Escalate
      }



    }

//    val system2 = ActorSystem("some-system")


    val process4 = parseProcess(xml4.stripMargin)
//    val process2 = parseProcess(xml2.stripMargin)
//    val process3 = parseProcess(xml3.stripMargin)

    val system = ActorSystem("bpmn")
    val processManager = system.actorOf(Props(classOf[Processes]), "proessmanager") // /user/processmanager
    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], process4), "process1")
//    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], process2), "process2")
//    processManager ! Processes.CreateProcess(Props(classOf[ProcessDefActor], process3), "process3")

    //    Thread.sleep(1000)
//     Thread.sleep(15000)
//     system.terminate()

  }
}