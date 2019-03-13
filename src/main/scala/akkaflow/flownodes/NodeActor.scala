package akkaflow.flownodes

import org.activiti.bpmn.model._
import akka.actor._

object NodeActor {
  
  case object Start
  
  def apply(node: FlowNode, processInstanceId: String)(implicit context: ActorContext): ActorRef = node match {
    case n: ServiceTask => context.actorOf(Props(classOf[ServiceTaskActor], n))
    case n: ParallelGateway => context.actorOf(Props(classOf[ParallelGatewayActor], n))
    case n: ExclusiveGateway => context.actorOf(Props(classOf[ExclusiveGatewayActor], n))
    case n: StartEvent => context.actorOf(Props(classOf[StartEventActor], n))
    case n: UserTask => context.actorOf(Props(classOf[UserTaskActor], n, processInstanceId))
    case n: EndEvent => context.actorOf(Props(classOf[EndEventActor], n))

  }
}
