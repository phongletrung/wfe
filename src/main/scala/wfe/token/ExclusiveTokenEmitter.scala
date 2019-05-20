package wfe.token

import akka.actor._
import org.camunda.bpm.model.bpmn.instance.Gateway
import wfe.token.Tok.Token

import scala.collection.JavaConverters._

/**
 * Emit a token on the first outgoing flow who's condition evaluates
 * to true, or the default condition otherwise.
 */
trait ExclusiveTokenEmitter extends TokenEmitter[Gateway] {
  def emitTokens(existingTokens: Seq[Token[_]], to: ActorRef) = {
    val potentialTargets = node.getOutgoing().asScala
    if (potentialTargets.size == 1) {
      sendAndDestroyTokens(existingTokens, Seq(potentialTargets.head), to)
    } else {
      if (existingTokens.size > 1) {
        throw new RuntimeException("should not happen")
      }
      val target = potentialTargets.find(evaluateCondition(_, existingTokens.head))
        //      .orElse(defaultFlow)
        .getOrElse {
        // BOOM!
        throw new RuntimeException("Didn't find a suitable outgoing flow!")
      }
      sendAndDestroyTokens(existingTokens, Seq(target), to)
    }


  }

//  def defaultFlow = Option(node..getOutgoing.getDefaultFlow).flatMap { sequenceFlowRef =>
//    node.getOutgoingFlows().asScala.find(_.getId == sequenceFlowRef)
//  }

}