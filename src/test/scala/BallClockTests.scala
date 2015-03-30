import org.scalatest._

/**
 * Created by nickstewart on 3/27/15.
 */
class BallClockTests extends FlatSpec with Matchers {
    "A Ball Clock" should "find the answer for 30 balls" in {
        val clock = BallClock(30)
        clock.run() should be (15)
    }
    it should "find the answer for 45 balls" in {
        val clock = BallClock(45)
        clock.run() should be (378)
    }
}
