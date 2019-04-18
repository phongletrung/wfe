package akkaflow.flownodes

import akka.actor._
import akkaflow.IncomingToken
import akkaflow.token.{UnconditionalTokenEmitter}
import org.camunda.bpm.model.bpmn.instance.ParallelGateway
import wfe.token.Tok.Token

import scala.collection.JavaConverters._

class ParallelGatewayActor(val node: ParallelGateway) extends Actor with ActorLogging with UnconditionalTokenEmitter {
  // Map from SequenceFlowRef to tokens
  var tokenBuffers: Map[String, Seq[Token[_]]] =
    node.getIncoming.asScala.map(_.getId -> Nil).toMap

  def receive = {
    case IncomingToken(token @ Token(id, v: Int), sequenceFlowRef) => {

      log.info("Received a token in Parallel Gateway")
      tokenBuffers += sequenceFlowRef -> (tokenBuffers(sequenceFlowRef) :+ token)

      // Emit tokens if we
      val headTokenOptions = tokenBuffers.map(_._2.headOption)

      if (headTokenOptions forall (_.isDefined)) {
        log.info("We have a token on every incoming sequence flow!")
        val tokens = headTokenOptions.map(_.get)
        tokenBuffers = tokenBuffers mapValues (_.tail)
        emitTokens(tokens.toSeq, sender)
      }
    }

    case IncomingToken(token @ Token(id, v: String), sequenceFlowRef) => {

      log.info("Received a token in Parallel Gateway")
      tokenBuffers += sequenceFlowRef -> (tokenBuffers(sequenceFlowRef) :+ token)

      // Emit tokens if we
      val headTokenOptions = tokenBuffers.map(_._2.headOption)

      if (headTokenOptions forall (_.isDefined)) {
        log.info("We have a token on every incoming sequence flow!")
        val tokens = headTokenOptions.map(_.get)
        tokenBuffers = tokenBuffers mapValues (_.tail)
        emitTokens(tokens.toSeq, sender)
      }
    }

    case IncomingToken(token @ Token(id, v: Boolean), sequenceFlowRef) => {

      log.info("Received a token in Parallel Gateway")
      tokenBuffers += sequenceFlowRef -> (tokenBuffers(sequenceFlowRef) :+ token)

      // Emit tokens if we
      val headTokenOptions = tokenBuffers.map(_._2.headOption)

      if (headTokenOptions forall (_.isDefined)) {
        log.info("We have a token on every incoming sequence flow!")
        val tokens = headTokenOptions.map(_.get)
        tokenBuffers = tokenBuffers mapValues (_.tail)
        //TODO
        emitTokens(tokens.toSeq, sender)
      }
    }

  }

}
