package wfe.flownodes

import akka.actor._
import org.camunda.bpm.model.bpmn.instance.ParallelGateway
import wfe.token.Tok.Token
import wfe.token.UnconditionalTokenEmitter
import wfe.{IncomingToken, ProcessManager}

import scala.collection.JavaConverters._

class ParallelGatewayActor(val nodeId: String, val process: String) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  // Map from SequenceFlowRef to tokens

  val node: ParallelGateway = ProcessManager.getFlowElementById(process, nodeId).asInstanceOf[ParallelGateway]

  var tokenBuffers: Map[String, Seq[Token[_]]] =
    node.getIncoming.asScala.map(_.getId -> Nil).toMap

  def receive = {
    case IncomingToken(token, sequenceFlowRef) => {

      log.info("Received a token in Parallel Gateway")
      //saves token in a map and waits until for every incoming sequence receives a token
      tokenBuffers += sequenceFlowRef -> (tokenBuffers(sequenceFlowRef) :+ token)

      // Emit tokens if we
      val headTokenOptions = tokenBuffers.map(_._2.headOption)
      //checks if there is one token for each incoming sequence flow (important for merge because if it is a split)
      if (headTokenOptions forall (_.isDefined)) {
        log.info("We have a token on every incoming sequence flow!")
        val tokens = headTokenOptions.map(_.get)
        tokenBuffers = tokenBuffers mapValues (_.tail)
        emitTokens(tokens.toSeq, sender)
      }
    }
  }

}
