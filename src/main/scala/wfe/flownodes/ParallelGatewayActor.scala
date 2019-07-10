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

  def receive: PartialFunction[Any, Unit] = {
    case IncomingToken(token, sequenceFlowRef) =>

      log.info("Parallel Gateway received a token")
      //saves token in a map and waits until for every incoming sequence receives a token
      tokenBuffers += sequenceFlowRef -> (tokenBuffers(sequenceFlowRef) :+ token)

      val headTokenOptions = tokenBuffers.map(_._2.headOption)
      //checks if there is one token for each incoming sequence flow (important for merge because if it is a split)
      if (headTokenOptions forall (_.isDefined)) {
        log.info("Every incoming sequence flow received a token!")
        val tokens = headTokenOptions.map(_.get)
        tokenBuffers = tokenBuffers mapValues (_.tail)
        emitTokens(tokens.toSeq, sender)
      }
  }

}
