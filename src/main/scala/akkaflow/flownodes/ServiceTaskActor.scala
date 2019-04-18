package akkaflow.flownodes

import akka.actor._
import akkaflow.IncomingToken
import akkaflow.ServiceTaskExamples.Evaluation
import akkaflow.token.UnconditionalTokenEmitter
import org.camunda.bpm.model.bpmn.instance.FlowNode
import wfe.token.Tok.Token

class ServiceTaskActor(val node: FlowNode) extends Actor with ActorLogging with UnconditionalTokenEmitter {
//  assert(node.getCamundaClass != null, s"Service task implementation must not be null, but is '${node.getCamundaClass}'")
//  val className = node.getCamundaClass
//
//  println(s"Classname: $className")

   val delegate: Evaluation = {
     var delegateName = node.getDomElement.getAttribute("http://camunda.org/schema/1.0/bpmn","class")
     val delegate = Class.forName(delegateName).getField("MODULE$").get(null).asInstanceOf[Evaluation]
     delegate
   }
  

  def receive = {
    case IncomingToken(token @ Token(id, v: Int), _) =>
      log.info("Servicetask received a token")
      val updatedToken = delegate(token)
      emitTokens(Seq(updatedToken), sender)
    case IncomingToken(token @ Token(id, v: String), _) =>
      log.info("Servicetask received a token")
      val updatedToken = delegate(token)
      log.info(token.toString)
      emitTokens(Seq(updatedToken), sender)
    case IncomingToken(token @ Token(id, v: Boolean), _) =>
      log.info("Servicetask received a token")
      val updatedToken = delegate(token)
      log.info(token.toString)
      emitTokens(Seq(updatedToken), sender)
  }
}
