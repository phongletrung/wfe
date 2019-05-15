package wfe.token
object Tok {

  case class Token[T](id: String, value: T)

  case class State(state: Map[String, Any])

/*
  val stringToken = Token("x", "string")
  val intToken = Token("y",0)
  val boolToken = Token("z",true)
  val mapToken = Token("0", State(null))

  val tok: Token[_] = stringToken
  tok match {
    case Token(id, s: String) => println(s"StringToken with value $s")
    case Token(id, i: Int) => println(s"IntToken with value $i")
    case Token(id, b: Boolean) => println(s"BoolToken with value $b")
  }

  val s = State(Map(

    "x" -> 42,
    "y" -> "horst"
  ))

  s.state.get("x") match {
    case None => //gibt's nicht
    case Some(s: String) =>
    case Some(i: Int) =>
    case Some(b: Boolean) =>
  }*/
}