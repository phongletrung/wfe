package akkaflow.flownodes

import org.activiti.bpmn.model.EndEvent
import akka.actor._
import akkaflow.IncomingToken
import akkaflow.token.UnconditionalTokenEmitter

class EndEventActor(val node: EndEvent) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  def receive = {
    case IncomingToken(token, _) => emitTokens(Seq(token), sender)
  }
}