package wfe

import akka.actor.{Actor, ActorLogging}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._

class ClusterListener extends Actor with ActorLogging {
  var memberCnt = 0


  // subscribe to cluster changes, re-subscribe when restart
  override def preStart(): Unit = {
    memberCnt = 0
    log.debug("starting up cluster listener...")
    Cluster(context.system).subscribe(self, InitialStateAsEvents, classOf[ClusterDomainEvent])
  }

  def receive: PartialFunction[Any, Unit] = {
    case state: CurrentClusterState ⇒
      log.debug("Current members: {}", state.members.mkString(", "))
    case MemberUp(member) =>
      log.debug("Member is Up: {}", member.address)

    case UnreachableMember(member) =>
      log.debug("Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) =>
      log.debug("Member is Removed: {} after {}",
        member.address, previousStatus)
      case LeaderChanged(member) =>
        log.info("Leader changed: " + member)
    case any: MemberEvent => log.info("Member Event: " + any.toString) // ignore
      }
  }
