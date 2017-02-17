/**
  * Created by wanglong on 17-2-17.
  */
import org.scalatest._
class interesting extends FlatSpec with Matchers{
  "a number nine" should "equals to 9" in {
    9 should be (9)
  }
}
