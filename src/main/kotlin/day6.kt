import java.io.File

private const val ADD = '+'
private const val MUL = '*'

fun day6() {
    val lines = File("./src/main/resources/day6Input.txt").readLines()
    val maxWidth = lines.maxOf { it.length }

    var grandTotal = 0L

    val operationNumbers = mutableListOf<Long>()
    var operator: Char? = null

    for (i in maxWidth - 1 downTo 0) {
        val colChars = lines.map {
            if (i < it.length) it[i] else ' '
        }

        if (colChars.all { it.isWhitespace() }) {
            grandTotal += operationNumbers.operate(operator)
            operationNumbers.clear()
            operator = null
        } else {
            operationNumbers.add(colChars.filter { it.isDigit() }.joinToString("").toLong())

            operator = colChars.firstOrNull { it in "+*" }
        }
    }

    if (operationNumbers.isNotEmpty() && operator != null) {
        grandTotal += operationNumbers.operate(operator)
    }

    println(grandTotal)
}

private fun List<Long>.operate(operation: Char?): Long {
    return when (operation) {
        ADD -> {
            fold(0L) { acc, i -> acc + i }
        }
        MUL -> {
            fold(1L) { acc, i -> acc * i }
        }
        else -> 0L
    }
}


fun main() {
    day6()
}