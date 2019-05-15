package wfe

import akka.serialization.Serializer
import com.google.gson.{Gson, GsonBuilder}
import org.camunda.bpm.model.bpmn.impl.instance.FlowNodeImpl

class CamundaSerializer extends Serializer {

  // If you need logging here, introduce a constructor that takes an ExtendedActorSystem.
  // class MyOwnSerializer(actorSystem: ExtendedActorSystem) extends Serializer
  // Get a logger using:
  // private val logger = Logging(actorSystem, this)

  // This is whether "fromBinary" requires a "clazz" or not
  def includeManifest: Boolean = true

  // Pick a unique identifier for your Serializer,
  // you've got a couple of billions to choose from,
  // 0 - 40 is reserved by Akka itself
  def identifier = 1412412414

  def gson: Gson = new GsonBuilder().create()

  // "toBinary" serializes the given object to an Array of Bytes
  def toBinary(obj: AnyRef): Array[Byte] = {
    println("trying to serialize camunda thing")
    val flowNodeClass = classOf[FlowNodeImpl]
    var bytes: Array[Byte] = obj match {
      case flowNodeClass =>
        var asString = gson.toJson(obj)
        asString.getBytes("US-ASCII")
      case _ =>
        println("Invalid type: " + obj.getClass)
        Array[Byte]()
    }
    bytes
  }

  // "fromBinary" deserializes the given array,
  // using the type hint (if any, see "includeManifest" above)
  def fromBinary(bytes: Array[Byte], clazz: Option[Class[_]]): AnyRef = {
    var asString: String = new String(bytes, "US-ASCII")

    var asd = clazz.getOrElse(classOf[FlowNodeImpl])
    gson.fromJson(asString, asd)
  }
}
