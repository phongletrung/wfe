package wfe.flownodes

import akka.actor._
import wfe.IncomingToken
import wfe.token.UnconditionalTokenEmitter
import org.camunda.bpm.model.bpmn.instance.EndEvent

class EndEventActor(val node: EndEvent) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  def receive = {
    case IncomingToken(token, _) =>
      log.info("Token received on {}, token is {}", this.node.getId, token)
      emitTokens(Seq(token), sender)
  }
}