package akkaflow.token

import scala.collection.JavaConverters.asScalaBufferConverter

import org.activiti.bpmn.model.FlowNode

import akka.actor._

/**
 * Emit a token on all outgoing flows of the node.
 */
trait UnconditionalTokenEmitter extends TokenEmitter[FlowNode] {
  def emitTokens(existingTokens: Seq[Token], to: ActorRef) = {
    val targets = node.getOutgoingFlows().asScala
    sendAndDestroyTokens(existingTokens, targets, to)
  }
}