package akkaflow.token

import scala.collection.JavaConverters._
import akka.actor._
import org.camunda.bpm.model.bpmn.instance.FlowNode

/**
 * Emit a token on all outgoing flows of the node.
 */
trait UnconditionalTokenEmitter extends TokenEmitter[FlowNode] {
  def emitTokens(existingTokens: Seq[Token], to: ActorRef) = {
    val targets = node.getOutgoing.asScala
    sendAndDestroyTokens(existingTokens, targets, to)
  }
}