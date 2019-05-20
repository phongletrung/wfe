package wfe

import akka.actor.{Actor, ActorRef, Props, actorRef2Scala}



object ProcessDefActor {
  case class StartProcess(variables: Map[String, Any] = Map.empty)

  sealed trait ProcessEvent
  case class ProcessStarted(processInstance: ActorRef) extends ProcessEvent
  case class ProcessFinished(processInstance: ActorRef, variables: Map[String, Any]) extends ProcessEvent
}

class ProcessDefActor(process: String) extends Actor {
  import ProcessDefActor._
  
  override def receive = {
    case msg: StartProcess => {
      val processInstanceId = nextId()
      val processInstanceActor = context.actorOf(Props(classOf[ProcessInstanceActor], processInstanceId, process))
      processInstanceActor ! msg
      sender ! processInstanceActor
    }
  }

  private var nextIdValue = 1
  private def nextId(): String = {
    val id = nextIdValue
    nextIdValue += 1
    id.toString
  }

}