package wfe.flownodes

import akka.actor._
import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway
import wfe.IncomingToken
import wfe.token.ExclusiveTokenEmitter

class ExclusiveGatewayActor(val node: ExclusiveGateway) extends Actor with ActorLogging with ExclusiveTokenEmitter {
  def receive = {
    case IncomingToken( token, _) =>
      log.info("Received token in Exclusive Gateway")
      emitTokens(Seq(token), sender)
  }
}
