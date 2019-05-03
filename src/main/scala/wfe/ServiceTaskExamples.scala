package wfe

import wfe.ServiceTaskExamples.Evaluation
import wfe.token.Tok
import wfe.token.Tok.Token

object ServiceTaskExamples {
  type Evaluation = Token[_] => Token[_]
}


object PrintGreaterThenSix extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        s.state.get("a") match {
          case Some(i: Int) => println("a is greater than 6")
          case _ => throw new RuntimeException("a has wrong type or is not set")
        }
        Token(id, s)
      case t: Token[_] => t
    }
  }
}

object PrintSmallerThenSix extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        s.state.get("a") match {
          case Some(i: Int) => println("a is smaller than 6")
          case _ => throw new RuntimeException("a has wrong type or is not set")
        }
        Token(id, s)
      case t: Token[_] => t
    }
  }
}

object IncreaseBy3 extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        val newMap = s.state.get("a") match {
          case None => s.state.updated("a", 3)
          case Some(i: Int) => s.state.updated("a", i + 3)
          case _ => throw new RuntimeException("a has wrong type")
        }
        println("a increased by 3")
        Token(id, Tok.State(newMap))
      case t: Token[_] => t
    }
  }
}

object SetToSix extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        println("a set to 6")
        Token(id, Tok.State(s.state.updated("a", 6)))


      case t: Token[_] => t
    }
  }
}


object SetToSixB extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        println("b set to 6")
        Token(id, Tok.State(s.state.updated("b", 6)))


      case t: Token[_] => t
    }
  }
}


object AddConflictedVariables extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        println("token received on addconflictedvariables", s.state)
        val conflictedElements = scala.collection.mutable.Set[String]()
        s.state.foreach {
          case (k: String, v) => if (k.contains("_token_")) conflictedElements.add(k.split("_token_").head)
        }
        println(conflictedElements)
        var curState = s.state
        conflictedElements.foreach(f => {
          var values = scala.collection.mutable.Seq[Int]()
          s.state.foreach {
            case (k: String, v: Int) => if (k.contains(f + "_token_")) {
              values = values :+ v
              curState = curState - k
            }
          }
          curState = curState.updated(f, values.sum)
        })
        println("token after addconflictedvariables", curState)
        Token(id, Tok.State(curState))
      case t: Token[_] => t
    }
  }
}

object MultiplyConflictedVariables extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        println("token received on addconflictedvariables", s.state)
        val conflictedElements = scala.collection.mutable.Set[String]()
        s.state.foreach {
          case (k: String, v) => if (k.contains("_token_")) conflictedElements.add(k.split("_token_").head)
        }
        println(conflictedElements)
        var curState = s.state
        conflictedElements.foreach(f => {
          var values = scala.collection.mutable.Seq[Int]()
          s.state.foreach {
            case (k: String, v: Int) => if (k.contains(f + "_token_")) {
              values = values :+ v
              curState = curState - k
            }
          }
          curState = curState.updated(f, values.product)
        })
        println("token after addconflictedvariables", curState)
        Token(id, Tok.State(curState))
      case t: Token[_] => t
    }
  }
}


object MaxConflictedVariables extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        println("token received on addconflictedvariables", s.state)
        val conflictedElements = scala.collection.mutable.Set[String]()
        s.state.foreach {
          case (k: String, v) => if (k.contains("_token_")) conflictedElements.add(k.split("_token_").head)
        }
        println(conflictedElements)
        var curState = s.state
        conflictedElements.foreach(f => {
          var values = scala.collection.mutable.Seq[Int]()
          s.state.foreach {
            case (k: String, v: Int) => if (k.contains(f + "_token_")) {
              values = values :+ v
              curState = curState - k
            }
          }
          curState = curState.updated(f, values.max)
        })
        println("token after addconflictedvariables", curState)
        Token(id, Tok.State(curState))
      case t: Token[_] => t
    }
  }
}

object MinConflictedVariables extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        println("token received on addconflictedvariables", s.state)
        val conflictedElements = scala.collection.mutable.Set[String]()
        s.state.foreach {
          case (k: String, v) => if (k.contains("_token_")) conflictedElements.add(k.split("_token_").head)
        }
        println(conflictedElements)
        var curState = s.state
        conflictedElements.foreach(f => {
          var values = scala.collection.mutable.Seq[Int]()
          s.state.foreach {
            case (k: String, v: Int) => if (k.contains(f + "_token_")) {
              values = values :+ v
              curState = curState - k
            }
          }
          curState = curState.updated(f, values.min)
        })
        println("token after addconflictedvariables", curState)
        Token(id, Tok.State(curState))
      case t: Token[_] => t
    }
  }
}

object IncreaseByEight extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        val newMap = s.state.get("a") match {
          case None => s.state.updated("a", 8)
          case Some(i: Int) => s.state.updated("a", i + 8)
          case _ => throw new RuntimeException("a has wrong type")
        }
        println("a increased by 8")
        Token(id, Tok.State(newMap))
      case t: Token[_] => t
    }
  }
}

object IncreaseByEightB extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        val newMap = s.state.get("b") match {
          case None => s.state.updated("b", 8)
          case Some(i: Int) => s.state.updated("b", i + 8)
          case _ => throw new RuntimeException("b has wrong type")
        }
        println("b increased by 8")
        Token(id, Tok.State(newMap))
      case t: Token[_] => t
    }
  }
}

object MultiplyWithTen extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        val newMap = s.state.get("a") match {
          case Some(i: Int) => s.state.updated("a", i * 10)
          case _ => throw new RuntimeException("a has wrong type")
        }
        println("a multiplied with 10")
        println(token)
        Token(id, Tok.State(newMap))

      case t: Token[_] => t
    }
  }
}

object MultiplyWithFive extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        val newMap = s.state.get("a") match {
          case Some(i: Int) => s.state.updated("a", i * 5)
          case _ => throw new RuntimeException("a has wrong type")
        }
        println("a multiplied with 5")
        println(token)
        Token(id, Tok.State(newMap))
      case t: Token[_] => t
    }
  }
}

object DoNothing extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case t: Token[_] => t
    }
  }
}


object SetToStringTest extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        var test = "testttt"
        println(s"String set to '$test")

        Token(id, Tok.State(s.state.updated("s", test)))


      case t: Token[_] => t
    }
  }
}

object CountAmountOfT extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, s: Tok.State) =>
        var number: Int = 0
        val newMap = s.state.get("s") match {
          case Some(s: String) =>
            number = s.count(_ == 't')
          case _ => throw new RuntimeException("a has wrong type")
        }
        println(s"word 'test' has $number 't's")
        Token(id, Tok.State(s.state.updated("a", number)))


      case t: Token[_] => t
    }
  }
}
