package akkaflow.flownodes

import akka.actor._
import akkaflow.IncomingToken
import akkaflow.token.UnconditionalTokenEmitter
import org.activiti.bpmn.model.ServiceTask

class ServiceTaskActor(val node: ServiceTask) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  assert(node.getImplementationType == "class", s"Service task implementation type must be 'class', but is '${node.getImplementationType}'")
  val className = node.getImplementation
  
  val delegate = Class.forName(className).getField("MODULE$").get(null).asInstanceOf[Function0[Unit]]
  def receive = {
    case IncomingToken(token, _) => {
      log.info("Servicetask received a token")
      delegate()
      emitTokens(Seq(token), sender)
    }
  }
}
