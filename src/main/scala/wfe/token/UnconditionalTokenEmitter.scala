package wfe.token

import scala.collection.JavaConverters._
import akka.actor._
import org.camunda.bpm.model.bpmn.instance.FlowNode
import wfe.token.Tok.Token

/**
 * Emit a token on all outgoing flows of the node.
 */
trait UnconditionalTokenEmitter extends TokenEmitter[FlowNode] {
  def emitTokens(existingTokens: Seq[Token[_]], to: ActorRef) = {
    val targets = node.getOutgoing.asScala
    if (targets.size == 1 && node.getIncoming.size() > 1) {
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
            case (k: String, v) => if (conflicted.contains(k)) result(k + "_token_" + id) = v else result(k) = v
          }
        case _ => println("Unable to merge token, no map")
      }
      var tok = Token(id, Tok.State(result.toMap))
      sendAndDestroyTokens(Seq(tok), targets, to)
    } else {
      sendAndDestroyTokens(existingTokens, targets, to)
    }
  }
}