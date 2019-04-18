package akkaflow.flownodes

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import akka.actor._
import akkaflow.token.UnconditionalTokenEmitter
import akkaflow.{IncomingToken, Task}
import org.camunda.bpm.model.bpmn.instance.UserTask

object UserTaskActor {

  /**
   * Task life cycle events that we send over the Akka EventStream
   * so a registered task repository can pick them up.
   */
  sealed trait TaskEvent
  case class TaskCreated(task: Task) extends TaskEvent
  case class TaskCompleted(task: Task) extends TaskEvent
}

class UserTaskActor(val node: UserTask, processInstanceId: String) extends Actor with ActorLogging with UnconditionalTokenEmitter {

  def receive = {
    case IncomingToken(token, _) => {
      val promise = Promise[Unit]()
      val task = Task(processInstanceId, node.getId, promise)
      context.system.eventStream.publish(UserTaskActor.TaskCreated(task))

      val currentSender = sender
      val currentEventStream = context.system.eventStream
      promise.future.onComplete { result =>
        emitTokens(token :: Nil, currentSender)
        currentEventStream.publish(UserTaskActor.TaskCompleted(task))
      }
    }
    case (x: Int, IncomingToken(token, _)) =>
      if (x > 4) println() else println()
  }
}
