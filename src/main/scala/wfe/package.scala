import wfe.token.Tok.Token

import scala.concurrent.Promise

package object wfe {
  
  case class Task(processInstanceId: String, nodeId: String, promise: Promise[Unit])
  case class CompleteUserTask(nodeRef: String)
  case class Continue()

  case class IncomingToken(token: Token[_], sequenceFlowRef: String)
  case class OutgoingToken(token: Token[_], sequenceFlowRef: String)
  case class CreateToken(oldtoken: Option[Token[_]], sequenceFlowRef: String)
  case class DestroyToken(token: Token[_])

}




