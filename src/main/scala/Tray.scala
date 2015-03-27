/**
 * BallClockTray
 * @author Nick Stewart
 */
trait ClockTray {
    def add(ball: Int): List[Int]
}

class Tray(max: Int, var balls: List[Int] = List.empty, val next: ClockTray = NilTray) extends ClockTray {
    def full: Boolean = balls.length == max

    override def add(ball: Int): List[Int] = {
        if(full) {
            val toAppend = balls.reverse
            balls = List.empty
            toAppend ::: next.add(ball)
        } else {
            balls = balls :+ ball
            List.empty
        }
    }

    override def toString = {
        balls.toString()
    }
}

class ClockQueue(var balls: List[Int]) {
    def pop: Int = {
        val b = balls.head
        balls = balls.tail
        b
    }
    def append(toAppend: List[Int]) = {
        balls = balls ::: toAppend
    }

    override def toString = {
        balls.toString()
    }
}

object NilTray extends ClockTray {
    override def add(ball: Int): List[Int] = List(ball)
}
