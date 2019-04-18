package akkaflow

import akkaflow.ServiceTaskExamples.Evaluation
import wfe.token.Tok.Token

object ServiceTaskExamples {
  type Evaluation = Token[_] => Token[_]


  val MultiplayWithFive: Evaluation = (token) => {
    token match {
      case Token(id, i: Int) => Token(id, i * 5)
      case t: Token[_] => t
    }

  }
  val MultiplayWithSix: Evaluation = (token) => {
    token match {
      case Token(id, i: Int) => Token(id, i * 6)
      case t: Token[_] => t
    }

  }


  val testDelegate: Evaluation = (token) => {
    token match {
      case Token(id, i: Int) =>
        println("Set a to 6")
        Token(id, i + 6)
      case t: Token[_] => t
    }

  }
}


object PrintGreater extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) =>
        if (i > 6) {
          println("a is greater than 6")
        } else {
          println("a is not greater than 6")
        }
        Token(id, i)
      case t: Token[_] => t
    }
  }
}


object TestDelegate extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) =>
        val newtoken = Token(id, i + 7)
        println(s"Increased token $id, from $i to ${newtoken.value}")
        newtoken
      case t: Token[_] => t
    }
  }
}

object TestDelegate2 extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) =>
        println("increased by 8")
        Token(id, i + 8)
      case t: Token[_] => t
    }
  }
}

object MultiplyWithSix extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) =>
        Token(id, i * 6)
      case t: Token[_] => t
    }
  }
  }

  object MultiplyWithFive extends Evaluation {
    override def apply(token: Token[_]): Token[_] = {
      token match {
        case Token(id, i: Int) => Token(id, i * 5)
        case t: Token[_] => t
      }

    }


}

object DoNothing extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) => Token(id, i * 5)
      case t: Token[_] => t
    }

  }


}