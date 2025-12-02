import java.io.File

val repeatPattern = Regex("""^(.+)\1+$""")

fun day2() {
    val ranges = File("./src/main/resources/day2Input.txt").readLines().flatMap { line ->
        val parts = line.split(",")
        parts.map {
            val (start, end) = it.split("-")
            LongRange(start.toLong(), end.toLong())
        }
    }

    var sum = 0L
    for (range in ranges) {
        for (n in range) {
            if (n.isInvalidID()) sum += n
        }
    }

    println(sum)
}

private fun Long.isInvalidID(): Boolean {
    return repeatPattern.matches(this.toString())
}

fun main() {
    day2()
}