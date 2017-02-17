/**
  * Created by wanglong on 17-2-15.
  */
import org.scalatest._
trait Prediction
trait Observation
case class PredicInt ( a:Int ) extends Prediction
case class Predictions( a:String ) extends Prediction
case class ObserDou(b:Double) extends Observation
case class Observations( b:String) extends Observation

class Checking {
  def check( pred:PredicInt, obser:ObserDou):Boolean= if (pred.a.equals(obser.b.toInt)) true else false
  def check( pred:PredicInt, obser:Observations):Boolean=if (pred.a==obser.b.toInt) true else false
  def check( pred:Predictions, obser:Observations):Boolean=if (pred.a==obser.b) true else false
}


object Main extends FlatSpec with Matchers  {

  def main(args: Array[String]): Unit = {
    println("starting prediction service")

    //test Int equals Float
    val pred =  PredicInt(4)
    val obser = ObserDou(4.00001)
    val check=new Checking
    assert(check.check(pred,obser))
    printChecking("float")
    pred.a should be (4)

    //test Int equals String
    val preds =  PredicInt(5)
    val obsers = Observations("5")
    assert(check.check(preds,obsers))
    //Unit test
    printChecking("string")

    //test String equals String
    val preds2 =  Predictions("5")
    val obsers2 = Observations("5")
    assert(check.check(preds2,obsers2))
    //Unit test
    printChecking("string")

    val numberOfPredictions = 10000000

    val beg = System.currentTimeMillis()
    if(arrayBased(obsers2,numberOfPredictions)) println("In arrayBased: true")
    println("Array based time:" + (System.currentTimeMillis() - beg))

    val beg1 = System.currentTimeMillis()
    if(onTheFly(obsers2, numberOfPredictions)) println("In onTheFly :true")
    println("On the fly time:" + (System.currentTimeMillis() - beg1))





  }

  private def arrayBased(obsers2: Observations,num:Int):Boolean = {
    val predictions = new Array[Int](num)
    (0 until num).foreach { i => predictions(i) = i}
    if(predictions.contains(obsers2.b.toInt))  true  else false
  }

  private def onTheFly(obsers2: Observations,num:Int):Boolean = {
    (0 until num).foreach {i=>if(i.equals(obsers2.b.toInt)) return true}
    false
  }

  val printChecking:String=>Unit= (first:String)=>println(" Checking Int and "+first+ " equality :true")
}

