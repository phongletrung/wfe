package wfe.flownodes

import akka.actor._
import org.camunda.bpm.model.bpmn.instance.StartEvent
import wfe.ProcessManager
import wfe.flownodes.NodeActor._
import wfe.token.UnconditionalTokenEmitter

class StartEventActor(val nodeId: String, val process: String) extends Actor with ActorLogging with UnconditionalTokenEmitter {

  val node: StartEvent = ProcessManager.getFlowElementById(process, nodeId).asInstanceOf[StartEvent]

  def receive: PartialFunction[Any, Unit] = {
    case Start => emitTokens(Nil, sender)
  }
}
