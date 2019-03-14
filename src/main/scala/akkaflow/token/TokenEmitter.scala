package akkaflow.token


import akka.actor.{ActorRef, actorRef2Scala}
import akkaflow.{CreateToken, DestroyToken, OutgoingToken}
import org.camunda.bpm.model.bpmn.instance.{FlowNode, SequenceFlow}

trait TokenEmitter[N <: FlowNode] {
  def node: N
  def emitTokens(existingTokens: Seq[Token], to: ActorRef)

  def sendAndDestroyTokens(existingTokens: Seq[Token], targets: Iterable[SequenceFlow], to: ActorRef) = {
    // Send existing tokens to targets
    existingTokens.zip(targets).foreach {
      case (token, target) =>
        to ! OutgoingToken(token, target.getId)
    }
    // Create new tokens
    targets.drop(existingTokens.size).foreach { target =>
      to ! CreateToken(target.getId)
    }
    // Destroy obsolete tokens
    existingTokens.drop(targets.size).foreach { obsoleteToken =>
      to ! DestroyToken(obsoleteToken)
    }
    
  }
}