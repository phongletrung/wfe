package wfe.util

import java.io.StringReader

import javax.xml.stream.XMLInputFactory

import scala.xml.Elem

object ProcessParser {

  def parseProcess(process: Elem) = {
    val definitions = <definitions xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:camunda="http://camunda.org/schema/1.0/bpmn" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" id="Definitions_1" targetNamespace="http://bpmn.io/schema/bpmn" exporter="Camunda Modeler" exporterVersion="1.9.0">
      >
                        { process }
                      </definitions>
    val reader = new StringReader(definitions.toString)
    val factory = XMLInputFactory.newInstance()
    val streamReader = factory.createXMLStreamReader(reader)
    val converter = null
    converter
  }
}


