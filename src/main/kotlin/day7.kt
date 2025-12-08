import java.io.File

private const val SPLITTER = '^'

fun day7() {
    val grid = File("./src/main/resources/day7Input.txt")
        .readLines()
        .map { it.toCharArray().toTypedArray() }
        .toTypedArray()

    val counts = grid.first().map {
        if (it == 'S') {
            1L
        } else 0L
    }.toTypedArray()

    for (rowIdx in 1 until grid.size) {
        for (colIdx in 0 until counts.size) {

            val cell = grid[rowIdx][colIdx]

            if (cell == SPLITTER) {
                val localCount = counts[colIdx]
                if (colIdx - 1 >= 0) {
                    counts[colIdx - 1] += localCount
                }
                if (colIdx + 1 < counts.size) {
                    counts[colIdx + 1] += localCount
                }
                counts[colIdx] = 0L
            }
        }
    }

    println(counts.sum())
}

fun main() {
    day7()
}