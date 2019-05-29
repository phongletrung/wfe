package wfe.flownodes

import akka.actor._
import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway
import wfe.token.ExclusiveTokenEmitter
import wfe.{IncomingToken, ProcessManager}

class ExclusiveGatewayActor(val nodeId: String, val process: String) extends Actor with ActorLogging with ExclusiveTokenEmitter {
  val node: ExclusiveGateway = ProcessManager.getFlowElementById(process, nodeId).asInstanceOf[ExclusiveGateway]

  def receive: PartialFunction[Any, Unit] = {
    case IncomingToken( token, _) =>
      log.info("Received token in Exclusive Gateway")
      emitTokens(Seq(token), sender)
  }
}
