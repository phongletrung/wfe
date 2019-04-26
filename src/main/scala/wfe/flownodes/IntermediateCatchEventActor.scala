package wfe.flownodes

import akka.actor.{Actor, ActorLogging}
import wfe.IncomingToken
import wfe.token.UnconditionalTokenEmitter
import org.camunda.bpm.model.bpmn.instance.IntermediateCatchEvent

import scala.concurrent.duration._


class IntermediateCatchEventActor(val node: IntermediateCatchEvent) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  def receive = {
    case IncomingToken(token, _) => {
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
}
