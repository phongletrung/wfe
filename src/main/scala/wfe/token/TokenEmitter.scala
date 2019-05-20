package wfe.token


import akka.actor.{ActorRef, actorRef2Scala}
import de.odysseus.el.ExpressionFactoryImpl
import de.odysseus.el.util.SimpleContext
import wfe.{CreateToken, DestroyToken, OutgoingToken}
import org.camunda.bpm.model.bpmn.instance.{FlowNode, SequenceFlow}
import wfe.token.Tok.Token

trait TokenEmitter[N <: FlowNode] {
  def node: N
  def emitTokens(existingTokens: Seq[Token[_]], to: ActorRef)

  def sendAndDestroyTokens(existingTokens: Seq[Token[_]], targets: Iterable[SequenceFlow], to: ActorRef) = {
    // Send existing tokens to targets
    existingTokens.zip(targets).foreach {
      case (token, target) =>
        to ! OutgoingToken(token, target.getId)
    }
    // Create new tokens for example if there are one imcoming and multiple outgoing sequenceflow
    //create copies of tokens
    //pa you have 5 targets and 2 tokens: first two targets will be dropped and creates 3 new tokens
    targets.drop(existingTokens.size).foreach { target =>
      if (existingTokens.size > 1)
        throw new RuntimeException("Dont know how to")
      to ! CreateToken(existingTokens.headOption, target.getId)
    }
    // Destroy obsolete tokens at the EndEventActor
    //still token but no targets
    existingTokens.drop(targets.size).foreach { obsoleteToken =>
      to ! DestroyToken(obsoleteToken)
    }
    
  }

  def evaluateCondition(flow: SequenceFlow, token: Token[_]): Boolean = flow.getConditionExpression match {
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
              case _ => println("Unknown variable", k, v)
            }
          }
      }
      var condition = conditionExpression.getTextContent
      if (condition.length > 0 && condition.charAt(0) != '$')
        condition = "$" + condition
      val e = factory.createValueExpression(context, condition, classOf[java.lang.Boolean])
      e.getValue(context) == true
    }
  }
}