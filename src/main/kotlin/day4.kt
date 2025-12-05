import java.io.File

private const val PAPER_ROLL = '@'
private const val REMOVE_PAPER = '.'
private const val FORKLIFT_THRESHOLD = 4

private val neighborOffsets = listOf(
    -1 to 0, // LEFT
    -1 to -1, // TOP-LEFT
    -1 to 1, // BOTTOM-LEFT
    0 to -1, // TOP
    0 to 1, // BOTTOM
    1 to 0, // RIGHT
    1 to -1, // TOP-RIGHT
    1 to 1, // BOTTOM-RIGHT
)

typealias Grid = Array<Array<Char>>

fun day4() {
    val grid = File("./src/main/resources/day4Input.txt")
        .readLines()
        .map { it.toCharArray().toTypedArray() }
        .toTypedArray()

    var acc = 0
    do {
        val result = grid.forklift()
        acc += result
    } while (result > 0)

    println(acc)
}

private fun Grid.isValidPosition(row: Int, column: Int): Boolean {
    val rowCount = size
    val columnCount = get(0).size
    return row in 0 until rowCount && column in 0 until columnCount
}

private fun Grid.forklift(): Int{
    return flatMapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, value ->
            if (this[rowIndex][colIndex] == PAPER_ROLL) {
                neighborOffsets.count { (dx, dy) ->
                    this.isValidPosition(rowIndex + dy, colIndex + dx) &&
                            this[rowIndex + dy][colIndex + dx] == PAPER_ROLL
                }.also {
                    if (it < FORKLIFT_THRESHOLD) {
                        this[rowIndex][colIndex] = REMOVE_PAPER
                    }
                }
            } else -1
        }
    }.count { it in 0 until FORKLIFT_THRESHOLD }
}

fun main() {
    day4()
}