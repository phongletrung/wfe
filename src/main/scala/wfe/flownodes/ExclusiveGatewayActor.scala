package wfe.flownodes

import akka.actor._
import wfe.IncomingToken
import wfe.token.ExclusiveTokenEmitter
import org.camunda.bpm.model.bpmn.instance.ExclusiveGateway
import wfe.token.Tok.Token

class ExclusiveGatewayActor(val node: ExclusiveGateway) extends Actor with ActorLogging with ExclusiveTokenEmitter {
  def receive = {
    case IncomingToken( token @ Token(id, int), _) =>

      log.info("Received token in Exclusive Gateway")
      emitTokens(Seq(token), sender)

    case IncomingToken(token, _) => {
      log.info("Received token in Exclusive Gateway")
      emitTokens(Seq(token), sender)
    }
  }
}
