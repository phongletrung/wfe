package wfe.flownodes

import akka.actor._
import org.camunda.bpm.model.bpmn.instance.EndEvent
import wfe.token.UnconditionalTokenEmitter
import wfe.{IncomingToken, ProcessManager}

class EndEventActor(val nodeId: String, val process: String) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  val node: EndEvent = ProcessManager.getFlowElementById(process, nodeId).asInstanceOf[EndEvent]

  def receive = {
    case IncomingToken(token, _) =>
      log.info("Token received on {}, token is {}", this.node.getId, token)
      emitTokens(Seq(token), sender)
  }
}