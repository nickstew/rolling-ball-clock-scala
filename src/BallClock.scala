import scala.io.StdIn

/**
 * Emulates a physical Rolling Ball Clock.
 * @author Nick Stewart
 */
class BallClock(var queue: List[Int],
                var minuteTray: List[Int] = List.empty,
                var fiveMinuteTray: List[Int] = List.empty,
                var hourTray: List[Int] = List.empty) {
    val ballsInInitQueue = queue.max
    val minuteMax = 4
    val fiveMinuteMax = 11
    val hourMax = 11

    /**
     * Runs the Rolling Ball Clock until the Queue matches the original Queue
     * @return Int of days before the Queue matches the original Queue ordering.
     */
    def run(): Int = {
        var minutes = 0
        val originalQueue = (1 to ballsInInitQueue).toList
        do{
            minuteTray = addMinute()
            minutes += 1
        }while(originalQueue != queue)
        minutes/60/24
    }

    def addMinute(): List[Int] = {
        if (minuteTray.size == minuteMax) {
            queue = queue ::: minuteTray.reverse
            fiveMinuteTray = addFiveMinutes()
            List.empty

        }else{
            val head = queue.head
            queue = queue.tail
            minuteTray :+ head
        }
    }
    private def addFiveMinutes(): List[Int] = {
        if(fiveMinuteTray.size == fiveMinuteMax) {
            queue = queue ::: fiveMinuteTray.reverse
            hourTray = addHour()
            List.empty
        } else{
            val head = queue.head
            queue = queue.tail
            fiveMinuteTray :+ head
        }
    }
    private def addHour(): List[Int] = {
        if(hourTray.size == hourMax) {
            queue = queue.tail ::: (hourTray.reverse :+ queue.head)
            List.empty
        } else{
            val head = queue.head
            queue = queue.tail
            hourTray :+ head
        }
    }

    /**
     * This toString was very helpful in Testing if my logic was working right
     * @return String that is a visual printout of what a user would see if there was an actual Rolling Ball Clock
     *         sitting in front of them.
     */
    override def toString: String = f"\nMinutes: $minuteTray" +
                                    f"\nFive Minutes: $fiveMinuteTray" +
                                    f"\nHours: $hourTray" +
                                    f"\nQueue: $queue"
}

/**
 * Companion Object for a Scala Class must be inside the same file.
 */
object BallClock extends App {
    /**
     * Main reason for companion object is the apply constructor see line 101 for usage.
     * @param balls max number of balls in the queue to start the clock.
     * @return a Ball Clock ready to be started.
     */
    def apply(balls: Int): BallClock = {
        new BallClock((1 to balls).toList)
    }

    /**
     * This is the starting point for the application.
     */
    override def main (args: Array[String]) {
        var list = List.empty[Int]
        do {
            list = list :+ StdIn.readInt()
        }
        while(list.last != 0)
        println()

        list = ((list.reverse).tail).reverse
        list.foreach(ballsInQueue => {
            val clock = BallClock(ballsInQueue)
            val days = clock.run()
            println(f"$ballsInQueue ball cycle after $days days")
        })
    }
}