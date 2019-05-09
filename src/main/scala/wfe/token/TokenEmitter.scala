package wfe.token


import akka.actor.{ActorRef, actorRef2Scala}
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
    existingTokens.drop(targets.size).foreach { obsoleteToken =>
      to ! DestroyToken(obsoleteToken)
    }
    
  }
}