import java.io.File

class DialMove(
    rawMove: String
) {

    val direction: Direction = if (rawMove.first() == 'L') {
        Direction.LEFT
    } else Direction.RIGHT

    private val unsignedDistance = rawMove.drop(1).toInt()

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
    val currentPosition: Int = 50
) {

    fun move(move: DialMove): Dial {
        return Dial(
            currentPosition = (100 + currentPosition + move.distance) % 100
        )
    }

}

fun day1() {
    val moves = File("./src/main/resources/day1Input.txt").readLines().map {
        DialMove(it)
    }

    var dial = Dial()

    println("The dial starts by pointing at ${dial.currentPosition}")
    var count = 0
    for (move in moves) {
        dial = dial.move(move)
        println("The dial is rotated $move to point at ${dial.currentPosition}.")
        if (dial.currentPosition == 0) count++
    }
    println("The dial password is: $count")
}

fun main() {
    day1()
}