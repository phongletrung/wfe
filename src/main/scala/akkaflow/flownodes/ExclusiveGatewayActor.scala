package akkaflow.flownodes

import org.activiti.bpmn.model.ExclusiveGateway
import akka.actor._
import akkaflow.IncomingToken
import akkaflow.token.ExclusiveTokenEmitter

class ExclusiveGatewayActor(val node: ExclusiveGateway) extends Actor with ActorLogging with ExclusiveTokenEmitter {
  def receive = {
    case IncomingToken(token, _) => {
      log.info("Received token in Exclusive Gateway")
      emitTokens(Seq(token), sender)
    }
  }
}
