package akkaflow.flownodes

import org.activiti.bpmn.model.StartEvent
import akka.actor._
import akkaflow.flownodes.NodeActor._
import akkaflow.token.UnconditionalTokenEmitter

class StartEventActor(val node: StartEvent) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  def receive = {
    case Start => emitTokens(Nil, sender)
  }
}
