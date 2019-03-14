package akkaflow.flownodes

import akka.actor._
import akkaflow.IncomingToken
import akkaflow.token.ExclusiveTokenEmitter
import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway

class ExclusiveGatewayActor(val node: ExclusiveGateway) extends Actor with ActorLogging with ExclusiveTokenEmitter {
  def receive = {
    case IncomingToken(token, _) => {
      log.info("Received token in Exclusive Gateway")
      emitTokens(Seq(token), sender)
    }
  }
}
