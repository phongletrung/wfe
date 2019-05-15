scalaVersion := "2.12.8"

name := "wfe"

libraryDependencies ++= Seq(

"de.heikoseeberger" %% "constructr" % "0.19.0",
  "de.heikoseeberger" %% "constructr-coordination-etcd" % "0.19.0" % Test,
  "de.aktey.akka.visualmailbox" %% "collector" % "1.1.0",

"de.odysseus.juel" % "juel-spi" % "2.2.7",
  "de.odysseus.juel" % "juel-api" % "2.2.7",
  "de.odysseus.juel" % "juel-impl" % "2.2.7",
  "org.camunda.bpm.model" % "camunda-bpmn-model" % "7.10.0",

  "com.typesafe.akka" %% "akka-actor" % "2.5.22",
  "com.typesafe.akka" %% "akka-agent" % "2.5.22",
 "com.typesafe.akka" %% "akka-cluster" % "2.5.22",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.5.22",
  "com.google.code.gson" % "gson" % "2.8.5",


"com.typesafe.akka" %% "akka-slf4j"   % "2.5.22",

"org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5",
  "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.22" % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test")

resolvers ++= Seq(
  "Alfresco Maven Repository" at "https://maven.alfresco.com/nexus/content/groups/public/")

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-encoding", "UTF-8",
  "-Xlint",
)

parallelExecution in Test := false

fork in test := true

javaOptions in test += "-Dconfig.resource=application-test.conf"


version in Docker := "latest"

dockerExposedPorts in Docker := Seq(1600)

dockerEntrypoint in Docker := Seq("sh", "-c", "bin/clustering $*")

dockerRepository := Some("phongletrung")

dockerBaseImage := "java"
enablePlugins(JavaAppPackaging)