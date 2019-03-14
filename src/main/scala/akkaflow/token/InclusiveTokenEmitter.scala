package akkaflow.token

import akka.actor._
import de.odysseus.el.ExpressionFactoryImpl
import de.odysseus.el.util.SimpleContext
import org.camunda.bpm.model.bpmn.instance.{Gateway, SequenceFlow}

import scala.collection.JavaConverters._

/**
  * Emit a token on the first outgoing flow who's condition evaluates
  * to true, or the default condition otherwise.
  */
trait InclusiveTokenEmitter extends TokenEmitter[Gateway] {
  def emitTokens(existingTokens: Seq[Token], to: ActorRef) = {
    val potentialTargets = node.getOutgoing().asScala

    val targets = potentialTargets.filter(evaluateCondition(_))
      //      .orElse(defaultFlow)
      // BOOM!
    if (targets.isEmpty){
      throw new RuntimeException("Didn't find a suitable outgoing flow!")
    }

    sendAndDestroyTokens(existingTokens, targets, to)
  }

  def evaluateCondition(flow: SequenceFlow) = flow.getConditionExpression match {
    case null => false // This is an unconditional flow
    case conditionExpression => {
      val factory = new ExpressionFactoryImpl
      val context = new SimpleContext
//      val asd = conditionExpression.getTextContent
      context.setVariable("input", factory.createValueExpression(530, java.lang.Integer.TYPE))
      val e = factory.createValueExpression(context, conditionExpression.getTextContent, java.lang.Boolean.TYPE)
      e.getValue(context) == true
      //      conditionExpression.toBoolean
    }
  }

  //  def defaultFlow = Option(node..getOutgoing.getDefaultFlow).flatMap { sequenceFlowRef =>
  //    node.getOutgoingFlows().asScala.find(_.getId == sequenceFlowRef)
  //  }

}