package wfe.flownodes

import akka.actor._
import wfe.flownodes.NodeActor._
import wfe.token.UnconditionalTokenEmitter
import org.camunda.bpm.model.bpmn.instance.StartEvent

class StartEventActor(val node: StartEvent) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  def receive = {
    case Start => emitTokens(Nil, sender)
  }
}
