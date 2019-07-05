Workflow Engine
===

Goal
---
Implemententing a prototype of a workflow/process engine, which can execute business process
modeled in BPMN distributed with Akka Clustering, Remoting and Docker.

The XML files _thesisExample1.xml_ and _thesisExample2.xml_ are the one explained in my master thesis.

---
## Requirements

You need the following tools:
  - Java SDK. 
  - sbt (sbt is a build tool for Scala, Java, and more.
        For general documentation, see http://www.scala-sbt.org/.)

## Tools which are used

  - [Docker](https://docs.docker.com/install/)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

##How to deploy


**_Run locally:_**

Run the `ClusterApp`.

**_Run distributed:_**


In SBT, just run `docker:publishLocal` to create a local docker container. 

To run the cluster, run `docker-compose up`. This will create 3 nodes, a seed and two regular members, called `seed`, `c1`, and `c2`
and will execute the business process distributed on 3 nodes.

**Add business processes:**

Model a new business process for example with the Camunda Modeler, drag and drop the XML
into a new XML file in this project.

**How to run a fdifferent business processes:**

In the `ClusterApp` exchange the XML File in line 22.

