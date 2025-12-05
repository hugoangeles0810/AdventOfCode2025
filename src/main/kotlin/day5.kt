import java.io.File

fun day5() {
    val input = File("./src/main/resources/day5Input.txt").readLines()
    val separatorIdx = input.indexOf("")
    val ranges = input.subList(0, separatorIdx).map {
        val (start, end) = it.split("-")
        LongRange(start.toLong(), end.toLong())
    }
    val items = input.subList(separatorIdx + 1, input.size).map(String::toLong)

    val result = items.count { id ->
        ranges.any { id in it }
    }

    println(result)
}

fun main() {
    day5()
}