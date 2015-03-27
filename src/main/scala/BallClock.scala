import scala.io.StdIn

/**
 * Emulates a physical Rolling Ball Clock.
 * @author Nick Stewart
 */
class BallClock(val ballsInInitQueue: Int,
                var queue: ClockQueue,
                var minuteTray: ClockTray,
                var fiveMinuteTray: ClockTray,
                var hourTray: ClockTray) {
    /**
     * Runs the Rolling Ball Clock until the Queue matches the original Queue
     * @return Int of days before the Queue matches the original Queue ordering.
     */
    def run(): Int = {
        var minutes = 0
        val originalQueue = (1 to ballsInInitQueue).toList
        do{
            queue.append(minuteTray.add(queue.pop))
            minutes += 1
        }while(originalQueue != queue.balls)
        minutes/60/24
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
        val hourTray = new Tray(11)
        val fiveMinuteTray = new Tray(11, next = hourTray)
        val minuteTray = new Tray(4, next = fiveMinuteTray)
        new BallClock(balls, new ClockQueue((1 to balls).toList), minuteTray, fiveMinuteTray, hourTray)
    }

    def time[R](block: => R): R = {
        val t0 = System.nanoTime()
        val result = block    // call-by-name
        val t1 = System.nanoTime()
        println("Elapsed time: " + (t1 - t0)/1000000000.0 + "s")
        result
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
            val days = time { clock.run() }
            println(f"$ballsInQueue ball cycle after $days days")
        })
    }
}