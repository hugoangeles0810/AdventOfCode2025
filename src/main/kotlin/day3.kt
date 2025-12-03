import java.io.File

private const val SIZE = 12

fun day3() {
    File("./src/main/resources/day3Input.txt")
        .readLines()
        .sumOf { it.joltage() }
        .also {
            println(it)
        }
}

private fun String.joltage(): Long {
    val joltage = StringBuilder()
    var left = 0
    var step = length - SIZE + 1
    repeat (SIZE) {
        val (idx, max) = substring(left, left + step).maxDigitWithIndex()
        joltage.append(max)
        left += idx + 1
        step = step - idx
    }
    return joltage.toString().toLong()
}

private fun String.maxDigitWithIndex(): Pair<Int, Int> =
        map { it.digitToInt() }
        .foldIndexed(0 to 0) { index, acc, number ->
            if (number > acc.second) {
                index to number
            } else acc
        }

fun main() {
    day3()
}