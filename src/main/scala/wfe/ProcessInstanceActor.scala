package wfe

import akka.actor.{Actor, ActorLogging, ActorRef, Deploy, actorRef2Scala}
import akka.cluster.{Cluster, Member}
import akka.remote.RemoteScope
import org.camunda.bpm.model.bpmn.instance.{FlowElement, FlowNode, Process, SequenceFlow, StartEvent}
import wfe.flownodes.NodeActor
import wfe.flownodes.NodeActor._
import wfe.token.Tok
import wfe.token.Tok.Token

import scala.collection.JavaConverters.collectionAsScalaIterableConverter


object ProcessInstanceActor {
  case class StartProcess(variables: Map[String, Any] = Map.empty)

  sealed trait ProcessEvent
  case class ProcessStarted(processInstance: ActorRef) extends ProcessEvent
  case class ProcessFinished(processInstance: ActorRef, variables: Map[String, Any]) extends ProcessEvent
}

//has the whole process model in process: Process
class ProcessInstanceActor(processInstanceId: String, processAsString: String) extends Actor with ActorLogging {
  import ProcessInstanceActor._

  /**
   * Map from tokens to the id of the flownode where the token
   * currently is.
   */
  //for debugging: can follow which token will be sent to which node
  var tokens: Map[Token[_], String] = Map()

  var variables: Map[String, Any] = Map()

  var process: Process = ProcessManager.parseProcess(processAsString)

  var flowNodeActors: Map[String, ActorRef] = _

  def createFlowNodeActors(): Map[String, ActorRef] = {
    val cluster = Cluster(context.system)
    val members = cluster.state.members.toArray
    var cur: Int = 0
    if (flowNodeActors == null)
      flowNodeActors = process.getFlowElements.asScala.collect {
        case node: FlowNode => node.getId -> {
          // round robin
          val target: Member = members.apply(cur % members.length)
          cur += 1
          NodeActor(node, processAsString, processInstanceId, Deploy(scope = RemoteScope(target.address))) //RemoteScope(target.address)))
        }
      }.toMap
    flowNodeActors
 }
    
  def receive: PartialFunction[Any, Unit] = {
    case StartProcess(vars) =>
      this.variables = vars
      createFlowNodeActors()
      startNode foreach { startNode =>
        val actor = flowNodeActors(startNode.getId)
        context.system.eventStream.publish(ProcessInstanceActor.ProcessStarted(self))
        actor ! Start
      }
    case CreateToken(oldtoken, sequenceFlowRef) =>
      //looks up where to go
      val sequenceFlow = process.getFlowElements.asScala.find(_.getId == sequenceFlowRef).head.asInstanceOf[SequenceFlow]
      val targetNode = sequenceFlow.getTarget.getId
      val target = flowNodeActors(targetNode)
      val token = createToken(oldtoken.getOrElse(createToken(Tok.State(Map()))).value)
      tokens += token -> targetNode
      target ! IncomingToken(token, sequenceFlowRef)

    case OutgoingToken(token, sequenceFlowRef) =>
      val sequenceFlow = process.getFlowElements.asScala.find(_.getId == sequenceFlowRef).head.asInstanceOf[SequenceFlow]
      val targetNode = sequenceFlow.getTarget.getId
      val target = flowNodeActors(targetNode)
      tokens += token -> targetNode
      target ! IncomingToken(token, sequenceFlowRef)

    case DestroyToken(token) =>
      tokens -= token
      if (tokens.isEmpty) {
        context.system.eventStream.publish(ProcessInstanceActor.ProcessFinished(self, variables))
        context.stop(self)
      }
  }


//  override def supervisorStrategy: SupervisorStrategy =
//    AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1.minute) {
//      case _: ArithmeticException => Resume
//    }

  def startNode: Option[FlowElement] = process.getFlowElements.asScala.find(_.isInstanceOf[StartEvent])

  def createToken[T](t:T) = Token(generateTokenId, t)

  var nextTokenId = 0
  def generateTokenId: String = {
    val id = nextTokenId
    nextTokenId += 1
    id.toString
  }
}
