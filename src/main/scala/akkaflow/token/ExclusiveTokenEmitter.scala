package akkaflow.token

import akka.actor._
import de.odysseus.el.ExpressionFactoryImpl
import de.odysseus.el.util.SimpleContext
import org.activiti.bpmn.model.{Gateway, SequenceFlow}

import scala.collection.JavaConverters.asScalaBufferConverter

/**
 * Emit a token on the first outgoing flow who's condition evaluates
 * to true, or the default condition otherwise.
 */
trait ExclusiveTokenEmitter extends TokenEmitter[Gateway] {
  def emitTokens(existingTokens: Seq[Token], to: ActorRef) = {
    val potentialTargets = node.getOutgoingFlows().asScala

    val target = potentialTargets.find(evaluateCondition(_))
      .orElse(defaultFlow)
      .getOrElse {
        // BOOM!
        throw new RuntimeException("Didn't find a suitable outgoing flow!")
      }

    sendAndDestroyTokens(existingTokens, Seq(target), to)
  }

  def evaluateCondition(flow: SequenceFlow) = flow.getConditionExpression match {
    case null => false // This is an unconditional flow
    case conditionExpression => {
      val factory = new ExpressionFactoryImpl
      val context = new SimpleContext
      context.setVariable("input", factory.createValueExpression(530, java.lang.Integer.TYPE))
      val e = factory.createValueExpression(context, conditionExpression, java.lang.Boolean.TYPE)
      e.getValue(context) == true
//      conditionExpression.toBoolean
    }
  }

  def defaultFlow = Option(node.getDefaultFlow).flatMap { sequenceFlowRef =>
    node.getOutgoingFlows().asScala.find(_.getId == sequenceFlowRef)
  }

}