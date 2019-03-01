import akkaflow.token.Token

import scala.concurrent.Promise

package object akkaflow {
  
  case class Task(processInstanceId: String, nodeId: String, promise: Promise[Unit])
  case class CompleteUserTask(nodeRef: String)
  case class Continue()

  case class IncomingToken(token: Token, sequenceFlowRef: String)
  case class OutgoingToken(token: Token, sequenceFlowRef: String)
  case class CreateToken(sequenceFlowRef: String)
  case class DestroyToken(token: Token)

}




