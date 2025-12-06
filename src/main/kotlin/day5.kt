import java.io.File

fun day5() {
    val input = File("./src/main/resources/day5Input.txt").readLines()
    val separatorIdx = input.indexOf("")
    val ranges = input.subList(0, separatorIdx).map {
        val (start, end) = it.split("-")
        LongRange(start.toLong(), end.toLong())
    }.sortedBy { it.start }

    val union = mutableListOf<LongRange>()
    var currentRange = ranges.first()

    for (i in 1 until ranges.size) {
        val nextRange = ranges[i]

        if (nextRange overlaps currentRange) {
            val newEnd = maxOf(currentRange.last, nextRange.last)
            currentRange = currentRange.first..newEnd
        } else {
            union.add(currentRange)
            currentRange = nextRange
        }
    }
    union.add(currentRange)

    println(union.sumOf { it.last - it.first + 1 })
}

private infix fun LongRange.overlaps(other: LongRange): Boolean {
    return maxOf(this.first, other.first) <= minOf(this.last, other.last)
}

fun main() {
    day5()
}