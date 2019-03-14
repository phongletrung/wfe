scalaVersion := "2.12.8"

name := "akkaflow"

libraryDependencies ++= Seq(
  "de.odysseus.juel" % "juel-spi" % "2.2.7",
  "de.odysseus.juel" % "juel-api" % "2.2.7",
  "de.odysseus.juel" % "juel-impl" % "2.2.7",
  "org.camunda.bpm.model" % "camunda-bpmn-model" % "7.10.0",

  "com.typesafe.akka" %% "akka-actor" % "2.5.21",
  "com.typesafe.akka" %% "akka-agent" % "2.5.21",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5",
  "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.21" % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test")

resolvers ++= Seq(
  "Alfresco Maven Repository" at "https://maven.alfresco.com/nexus/content/groups/public/")

scalacOptions ++= Seq("-deprecation", "-feature")

parallelExecution in Test := false

fork in test := true

javaOptions in test += "-Dconfig.resource=application-test.conf"