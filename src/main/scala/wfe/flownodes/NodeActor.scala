package wfe.flownodes

import akka.actor._
import org.camunda.bpm.model.bpmn.instance._

object NodeActor {
  
  case object Start
  
  def apply(node: FlowNode, process: String, processInstanceId: String, d: Deploy)(implicit context: ActorContext): ActorRef = node match {
    case n: ServiceTask => context.actorOf(Props(classOf[ServiceTaskActor], n.getId, process).withDeploy(d))
    case n: ParallelGateway => context.actorOf(Props(classOf[ParallelGatewayActor], n.getId, process).withDeploy(d))
    case n: IntermediateCatchEvent => context.actorOf(Props(classOf[IntermediateCatchEventActor], n.getId, process).withDeploy(d))
    case n: InclusiveGateway => context.actorOf(Props(classOf[InclusiveGatewayActor], n.getId, process).withDeploy(d))
    case n: ExclusiveGateway => context.actorOf(Props(classOf[ExclusiveGatewayActor], n.getId, process).withDeploy(d))
    case n: StartEvent => context.actorOf(Props(classOf[StartEventActor], n.getId, process).withDeploy(d))
    case n: EndEvent => context.actorOf(Props(classOf[EndEventActor], n.getId, process).withDeploy(d))
  }
}
