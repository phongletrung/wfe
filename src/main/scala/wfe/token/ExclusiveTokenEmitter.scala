package wfe.token

import akka.actor._
import de.odysseus.el.ExpressionFactoryImpl
import de.odysseus.el.util.SimpleContext
import org.camunda.bpm.model.bpmn.instance.{Gateway, SequenceFlow}
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

  def evaluateCondition(flow: SequenceFlow, token: Token[_]) = flow.getConditionExpression match {
    case null => false // This is an unconditional flow
    case conditionExpression => {
      val factory = new ExpressionFactoryImpl
      val context = new SimpleContext

      token match {
        case Token(id: String, s: Tok.State) =>
          s.state.foreach {
            case (k, v) => v match {
              case st: String => context.setVariable(k, factory.createValueExpression(st, classOf[String]))
              case i: Integer => context.setVariable(k, factory.createValueExpression(i, classOf[Integer]))
              case b: Boolean => context.setVariable(k, factory.createValueExpression(b, classOf[java.lang.Boolean]))
            }
          }
      }
      val e = factory.createValueExpression(context, conditionExpression.getTextContent, classOf[java.lang.Boolean])
      e.getValue(context) == true
//      conditionExpression.toBoolean
    }
  }

//  def defaultFlow = Option(node..getOutgoing.getDefaultFlow).flatMap { sequenceFlowRef =>
//    node.getOutgoingFlows().asScala.find(_.getId == sequenceFlowRef)
//  }

}