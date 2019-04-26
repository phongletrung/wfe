package wfe

import wfe.ServiceTaskExamples.Evaluation
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


object PrintGreaterThenSix extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) =>

          println("a is greater than 6")
        Token(id, i)
      case t: Token[_] => t
    }
  }
}

object PrintSmallerThenSix extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) =>

        println("a is not greater than 6")
        Token(id, i)
      case t: Token[_] => t
    }
  }
}



object TestDelegate extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) =>
        val newtoken = Token(id, i + 3)
        println(s"Increased token $id, from $i to ${newtoken.value}")
        newtoken
      case t: Token[_] => t
    }
  }
}

object SetToSix extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) =>
        val newtoken = Token(id, 6)
        println("Variable set to 6")
        print(newtoken)
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

object MultiplyWithTen extends Evaluation {
  override def apply(token: Token[_]): Token[_] = {
    token match {
      case Token(id, i: Int) =>
        val newtoken = Token(id, 1*10)
        println("Variable mutiplied with  10")

        println(newtoken)
        newtoken
      case t: Token[_] => t
    }
  }
  }

  object MultiplyWithFive extends Evaluation {
    override def apply(token: Token[_]): Token[_] = {
      token match {
        case Token(id, i: Int) =>
          val newtoken = Token(id, 1*5)
          println("Variable mutiplied with  5")
          println(newtoken)
          newtoken
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
