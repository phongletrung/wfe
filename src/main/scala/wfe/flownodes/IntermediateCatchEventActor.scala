package wfe.flownodes

import akka.actor.{Actor, ActorLogging}
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent
import wfe.token.UnconditionalTokenEmitter
import wfe.{IncomingToken, ProcessManager}

import scala.concurrent.duration._


class IntermediateCatchEventActor(val nodeId: String, val process: String) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  val node: IntermediateCatchEvent = ProcessManager.getFlowElementById(process, nodeId).asInstanceOf[IntermediateCatchEvent]

  def receive: PartialFunction[Any, Unit] = {
    case IncomingToken(token, _) =>
      log.info("Received token in IntermediateCatchEvent")
      log.info("waiting")
      val originalSender = sender()
      context.system.scheduler.scheduleOnce(10 second) {
        println(token)
        println(originalSender)
        emitTokens(Seq(token), originalSender)
      }(context.system.getDispatcher)
      println(context.system.getDispatcher)
  }
}
