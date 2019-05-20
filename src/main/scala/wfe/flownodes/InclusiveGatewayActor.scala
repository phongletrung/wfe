package wfe.flownodes

import akka.actor._
import org.camunda.bpm.model.bpmn.instance.InclusiveGateway
import wfe.token.InclusiveTokenEmitter
import wfe.{IncomingToken, ProcessManager}

class InclusiveGatewayActor(val nodeId: String, val process: String) extends Actor with ActorLogging with InclusiveTokenEmitter {
  val node: InclusiveGateway = ProcessManager.getFlowElementById(process, nodeId).asInstanceOf[InclusiveGateway]

  def receive = {
    case IncomingToken(token, _) => {
      log.info("Received token in Inclusive Gateway")
      emitTokens(Seq(token), sender)
    }
  }
}
