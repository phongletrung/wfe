package wfe.flownodes

import akka.actor._
import org.camunda.bpm.model.bpmn.instance.InclusiveGateway
import wfe.token.Tok.Token
import wfe.token.{InclusiveTokenEmitter, Tok}
import wfe.{IncomingToken, ProcessManager}


class InclusiveGatewayActor(val nodeId: String, val process: String) extends Actor with ActorLogging with InclusiveTokenEmitter {
  val node: InclusiveGateway = ProcessManager.getFlowElementById(process, nodeId).asInstanceOf[InclusiveGateway]

  var tokenBuffers: Seq[Token[_]] = Seq()
  var targetLength: Option[Int] = None

  def receive = {
    case IncomingToken(token, sequenceFlowRef) => {
      if (node.getIncoming.size() > 1) {
        targetLength = token match {
          case Token(id, s: Tok.State) => s.state.get("orjoin" + "_targets").asInstanceOf[Option[Int]]
        }
        tokenBuffers = tokenBuffers :+ token
        //checks if there is one token for each incoming sequence flow (important for merge because if it is a split)
        if (tokenBuffers.size >= targetLength.getOrElse(Int.MaxValue)) {
          log.info("InclusiveGatewayActor: We have a token on every incoming sequence flow!")
          emitTokens(tokenBuffers, sender)
        }
      } else {
        emitTokens(Seq(token), sender)
      }
    }
  }
}
