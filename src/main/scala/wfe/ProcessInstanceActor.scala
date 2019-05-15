package wfe

import akka.actor.{Actor, ActorLogging, ActorRef, Deploy, LocalScope, actorRef2Scala}
import akka.cluster.Cluster
import org.camunda.bpm.model.bpmn.instance.{FlowElement, FlowNode, Process, SequenceFlow, StartEvent}
import wfe.ProcessDefActor.StartProcess
import wfe.flownodes.NodeActor
import wfe.flownodes.NodeActor._
import wfe.token.Tok
import wfe.token.Tok.Token

import scala.collection.JavaConverters.collectionAsScalaIterableConverter

object ProcessInstanceActor {
  case object GetVariables
}
//has the whole process model in process: Process
class ProcessInstanceActor(processInstanceId: String, process: Process) extends Actor with ActorLogging {
  import ProcessInstanceActor._

  /**
   * Map from tokens to the id of the flownode where the token
   * currently is.
   */
  //for debugging: can follow which token will be sent to which node
  var tokens: Map[Token[_], String] = Map()

  var variables: Map[String, Any] = Map()

  var flowNodeActors: Map[String, ActorRef] = _

  def createFlowNodeActors(): Map[String, ActorRef] = {
    val cluster = Cluster(context.system)
    val members = cluster.state.members
    val other = members.find(!_.address.equals(cluster.selfAddress))
    val target = other.getOrElse(cluster.selfMember).address
    println("Main node is me", process)

    if (flowNodeActors == null)
      flowNodeActors = process.getFlowElements.asScala.collect {
        case node: FlowNode => node.getId -> NodeActor(node, processInstanceId, Deploy(scope = LocalScope)) // Deploy(scope = RemoteScope(target)))
      }.toMap
    flowNodeActors
 }
    
  def receive = {
    case StartProcess(variables) => {
      this.variables = variables
      createFlowNodeActors()
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
      //looks up where to go
      val sequenceFlow = process.getFlowElements.asScala.find(_.getId == sequenceFlowRef).head.asInstanceOf[SequenceFlow]
      val targetNode = sequenceFlow.getTarget.getId
      val target = flowNodeActors(targetNode)
      val token = createToken(oldtoken.getOrElse(createToken(Tok.State(Map()))).value)
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


//  override def supervisorStrategy: SupervisorStrategy =
//    AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1.minute) {
//      case _: ArithmeticException => Resume
//    }

  def startNode: Option[FlowElement] = process.getFlowElements.asScala.find(_.isInstanceOf[StartEvent])

  def createToken[T](t:T) = Token(generateTokenId, t)

  var nextTokenId = 0
  def generateTokenId = {
    val id = nextTokenId
    nextTokenId += 1
    id.toString
  }
}
