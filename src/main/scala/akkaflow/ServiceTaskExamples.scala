package akkaflow

import akkaflow.token.Token

object TestDelegate extends Function1[Token, Unit] {
  var executions = 0
  override def apply(token: Token) {
    println(token)
//    token.set("input", 530)
    executions += 6
  }
}

object PrintGreater extends Function1[Token, Unit] {
  var executions = 0
  override def apply(token: Token) {
    if (executions > 3) {
      println("Irgendwas groessser 3")
    }else{
      println("kleiner/gleich als 3")
    }

  }
}

object MultiplayWithFive extends Function1[Token, Unit] {
  override def apply(token: Token) {
    println(token)
    //    token. get information)
//    multiply
//    token.set

  }
}



