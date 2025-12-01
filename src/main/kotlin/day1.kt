import java.io.File

class DialMove(
    rawMove: String
) {

    val direction: Direction = if (rawMove.first() == 'L') {
        Direction.LEFT
    } else Direction.RIGHT

    val unsignedDistance = rawMove.drop(1).toInt()

    val distance: Int = if (direction == Direction.LEFT) -unsignedDistance else {
        unsignedDistance
    }

    override fun toString(): String {
        return "${direction.name.first()}$unsignedDistance"
    }

    enum class Direction {
        LEFT, RIGHT;
    }
}

class Dial(
    var currentPosition: Int = 50
) {

    var zeroClicks = 0

    fun move(move: DialMove) {
        val distToFirstZero = if (move.direction == DialMove.Direction.RIGHT) {
            100 - currentPosition
        } else {
            if (currentPosition == 0) 100 else currentPosition
        }

        if (move.unsignedDistance >= distToFirstZero) {
            zeroClicks += 1 + (move.unsignedDistance - distToFirstZero) / 100
        }

        val newPos = (currentPosition + move.distance) % 100
        currentPosition = if (newPos < 0) newPos + 100 else newPos
    }

}

fun day1() {
    val moves = File("./src/main/resources/day1Input.txt").readLines().map {
        DialMove(it)
    }

    val dial = Dial()

    println("The dial starts by pointing at ${dial.currentPosition}")
    for (move in moves) {
        dial.move(move)
        println("The dial is rotated $move to point at ${dial.currentPosition}.")
    }
    println("The dial password is: ${dial.zeroClicks}")
}

fun main() {
    day1()
}