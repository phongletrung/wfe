package akkaflow.flownodes

import akka.actor._
import akkaflow.IncomingToken
import akkaflow.token.InclusiveTokenEmitter
import org.camunda.bpm.model.bpmn.instance.InclusiveGateway

class InclusiveGatewayActor(val node: InclusiveGateway) extends Actor with ActorLogging with InclusiveTokenEmitter {
  def receive = {
    case IncomingToken(token, _) => {
      log.info("Received token in Inclusive Gateway")
      emitTokens(Seq(token), sender)
    }
  }
}
