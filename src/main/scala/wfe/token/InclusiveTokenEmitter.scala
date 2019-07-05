package wfe.token

import akka.actor._
import org.camunda.bpm.model.bpmn.instance.{FlowNode, Gateway}
import wfe.token.Tok.Token

import scala.collection.JavaConverters._

/**
  * Emit a token on the first outgoing flow who's condition evaluates
  * to true, or the default condition otherwise.
  */
trait InclusiveTokenEmitter extends TokenEmitter[Gateway] {
  def emitTokens(existingTokens: Seq[Token[_]], to: ActorRef) = {
    val potentialTargets = node.getOutgoing().asScala

    //case: join gateway: one output, multiply inputs
    if (potentialTargets.size == 1 && node.getIncoming.size() > 1) {
      var keySet = scala.collection.mutable.Set[String]()
      var conflicted = scala.collection.mutable.Set[String]()
      var result = scala.collection.mutable.Map[String, Any]()
      val id = existingTokens.head.id
      existingTokens.foreach {
        case Token(id: String, s: Tok.State) =>
          s.state.foreach {
            case (k: String, v) => if (keySet.contains(k)) conflicted.add(k) else keySet.add(k)
          }
        case _ => println("Unable to merge token, no map")
      }
      existingTokens.foreach {
        case Token(id: String, s: Tok.State) =>
          s.state.foreach {
            case (k: String, v) => {
              if (!k.equals("orjoin" + "_targets")) {
                if (conflicted.contains(k)) result(k + "_token_" + id) = v else result(k) = v
              }
            }
          }
        case _ => println("Unable to merge token, no map")
      }
      var tok = Token(id, Tok.State(result.toMap))
      sendAndDestroyTokens(Seq(tok), potentialTargets, to)
    } else {

      val targets = potentialTargets.filter(evaluateCondition(_, existingTokens.head))
      //      .orElse(defaultFlow)
      val numTargets = targets.size
      //if left side of or (split)
      if (numTargets > 1) {
        val curToken = existingTokens.head
        // find the corresponding merge
        val mergeName = "orjoin"
        val newToken =
          curToken match {
            case Token(id, s: Tok.State) =>
              Token(id, Tok.State(s.state.updated(mergeName + "_targets", numTargets)))
            case t: Token[_] => t
          }
        sendAndDestroyTokens(Seq(newToken), targets, to)
      }
      if (targets.isEmpty){
        throw new RuntimeException("Didn't find a suitable outgoing flow!")
      }

    }
  }

  def findCorrespondingMergeNode(start: Seq[FlowNode], asd: Int) = {

  }

  //  def defaultFlow = Option(node..getOutgoing.getDefaultFlow).flatMap { sequenceFlowRef =>
  //    node.getOutgoingFlows().asScala.find(_.getId == sequenceFlowRef)
  //  }

}