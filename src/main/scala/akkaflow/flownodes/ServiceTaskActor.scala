package akkaflow.flownodes

import akka.actor._
import akkaflow.IncomingToken
import akkaflow.token.UnconditionalTokenEmitter
import org.camunda.bpm.model.bpmn.instance.ServiceTask

class ServiceTaskActor(val node: ServiceTask) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  assert(node.getCamundaClass != null, s"Service task implementation must not be null, but is '${node.getCamundaClass}'")
  val className = node.getCamundaClass

  val delegate = Class.forName(className).getField("MODULE$").get(null).asInstanceOf[Function0[Unit]]
  def receive = {
    case IncomingToken(token, _) => {
      log.info("Servicetask received a token")
      delegate()
      emitTokens(Seq(token), sender)
    }
  }
}
