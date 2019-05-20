package wfe.flownodes

import akka.actor._
import wfe.{IncomingToken, ProcessManager}
import wfe.ServiceTaskExamples.Evaluation
import wfe.token.UnconditionalTokenEmitter
import org.camunda.bpm.model.bpmn.instance.FlowNode
import wfe.token.Tok.Token

class ServiceTaskActor(val nodeId: String, val process: String) extends Actor with ActorLogging with UnconditionalTokenEmitter {
//  assert(node.getCamundaClass != null, s"Service task implementation must not be null, but is '${node.getCamundaClass}'")
//  val className = node.getCamundaClass
//
//  println(s"Classname: $className")

  //finds out which kind of ServiceTask it is
  //which class should be loaded from the xml
  //had to change it because return values from Camunda where different (return Token instead of Unit)

  val node: FlowNode = ProcessManager.getFlowElementById(process, nodeId).asInstanceOf[FlowNode]

   val delegate: Evaluation = {
     var delegateName = node.getDomElement.getAttribute("http://camunda.org/schema/1.0/bpmn","class")
     val delegate = Class.forName(delegateName).getField("MODULE$").get(null).asInstanceOf[Evaluation]
     delegate
   }
  

  def receive = {
    case IncomingToken(token: Token[_], _) =>
      log.info("Servicetask received a token")
      val updatedToken = delegate(token)
      emitTokens(Seq(updatedToken), sender)
  }
}
