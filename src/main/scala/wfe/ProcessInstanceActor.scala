package wfe

import akka.actor.SupervisorStrategy.Resume
import akka.actor.{Actor, ActorLogging, ActorRef, AllForOneStrategy, SupervisorStrategy, actorRef2Scala}
import wfe.ProcessDefActor.StartProcess
import wfe.flownodes.NodeActor
import wfe.flownodes.NodeActor._
import org.camunda.bpm.model.bpmn.instance.{FlowElement, FlowNode, Process, SequenceFlow, StartEvent}
import wfe.token.Tok.Token

import scala.collection.JavaConverters.collectionAsScalaIterableConverter
import scala.concurrent.duration._

object ProcessInstanceActor {
  case object GetVariables
}

class ProcessInstanceActor(processInstanceId: String, process: Process) extends Actor with ActorLogging {
  import ProcessInstanceActor._

  /**
   * Map from tokens to the id of the flownode where the token
   * currently is.
   */
  var tokens: Map[Token[_], String] = Map()

  var variables: Map[String, Any] = Map()

  val flowNodeActors: Map[String, ActorRef] =
    process.getFlowElements.asScala.collect {
      case node: FlowNode => node.getId -> NodeActor(node, processInstanceId)
    }.toMap
    
  def receive = {
    case StartProcess(variables) => {
      this.variables = variables
      startNode foreach { startNode =>
        val actor = flowNodeActors(startNode.getId)
        context.system.eventStream.publish(ProcessDefActor.ProcessStarted(self))
        actor ! Start
      }
    }
    case OutgoingToken(token, sequenceFlowRef) => {
      val sequenceFlow = process.getFlowElements.asScala.find(_.getId == sequenceFlowRef).head.asInstanceOf[SequenceFlow]
      val targetNode = sequenceFlow.getTarget.getId
      val target = flowNodeActors(targetNode)
      tokens += token -> targetNode
      target ! IncomingToken(token, sequenceFlowRef)
    }
    case CreateToken(oldtoken, sequenceFlowRef) => {
      val sequenceFlow = process.getFlowElements.asScala.find(_.getId == sequenceFlowRef).head.asInstanceOf[SequenceFlow]
      val targetNode = sequenceFlow.getTarget.getId
      val target = flowNodeActors(targetNode)
      val token = createToken(oldtoken.getOrElse(createToken(0)).value)
      tokens += token -> targetNode
      target ! IncomingToken(token, sequenceFlowRef)
    }
    case DestroyToken(token) => {
      tokens -= token
      if (tokens.isEmpty) {
        context.system.eventStream.publish(ProcessDefActor.ProcessFinished(self, variables))
        context.stop(self)
      }
    }

    case m @ CompleteUserTask(nodeRef) => {
      val target = flowNodeActors(nodeRef)
      target ! m
    }

    case GetVariables => sender ! variables
  }


  override def supervisorStrategy: SupervisorStrategy =
    AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1.minute) {
      case _: ArithmeticException => Resume
    }

  def startNode: Option[FlowElement] = process.getFlowElements.asScala.find(_.isInstanceOf[StartEvent])

  def createToken[T](t:T) = Token(generateTokenId, t)

  var nextTokenId = 0
  def generateTokenId = {
    val id = nextTokenId
    nextTokenId += 1
    id.toString
  }
}
