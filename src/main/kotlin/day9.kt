import java.io.File
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

data class Coord(val x: Long, val y: Long) {
    fun areaWith(other: Coord): Long {
        return (abs(x - other.x) + 1) * (abs(y - other.y) + 1)
    }
}

fun isOnSegment(px: Double, py: Double, p1: Coord, p2: Coord): Boolean {
    val cp = (py - p1.y) * (p2.x - p1.x) - (px - p1.x) * (p2.y - p1.y)
    if (abs(cp) > 0.0001) return false

    val dp = (px - p1.x) * (p2.x - p1.x) + (py - p1.y) * (p2.y - p1.y)
    if (dp < 0) return false
    val lenSq = (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y)
    if (dp > lenSq) return false

    return true
}

fun isPointInsideOrOnBoundary(x: Double, y: Double, polygon: List<Coord>): Boolean {
    var inside = false
    val n = polygon.size
    var j = n - 1

    for (i in 0 until n) {
        if (isOnSegment(x, y, polygon[i], polygon[j])) return true
        j = i
    }

    j = n - 1
    for (i in 0 until n) {
        val xi = polygon[i].x.toDouble()
        val yi = polygon[i].y.toDouble()
        val xj = polygon[j].x.toDouble()
        val yj = polygon[j].y.toDouble()

        val intersect = ((yi > y) != (yj > y)) &&
                (x < (xj - xi) * (y - yi) / (yj - yi) + xi)
        if (intersect) inside = !inside
        j = i
    }
    return inside
}

fun doesEdgeCutRectangle(minX: Long, maxX: Long, minY: Long, maxY: Long, polygon: List<Coord>): Boolean {
    val n = polygon.size
    var j = n - 1
    for (i in 0 until n) {
        val p1 = polygon[j]
        val p2 = polygon[i]

        if (p1.x == p2.x) {
            val edgeX = p1.x
            if (edgeX > minX && edgeX < maxX) {
                val edgeMinY = min(p1.y, p2.y)
                val edgeMaxY = max(p1.y, p2.y)
                if (max(minY, edgeMinY) < min(maxY, edgeMaxY)) {
                    return true
                }
            }
        }

        else if (p1.y == p2.y) {
            val edgeY = p1.y
            if (edgeY > minY && edgeY < maxY) {
                val edgeMinX = min(p1.x, p2.x)
                val edgeMaxX = max(p1.x, p2.x)
                if (max(minX, edgeMinX) < min(maxX, edgeMaxX)) {
                    return true
                }
            }
        }
        j = i
    }
    return false
}

fun isValidRectangle(c1: Coord, c2: Coord, polygon: List<Coord>): Boolean {
    val minX = min(c1.x, c2.x)
    val maxX = max(c1.x, c2.x)
    val minY = min(c1.y, c2.y)
    val maxY = max(c1.y, c2.y)

    val centerX = (minX + maxX) / 2.0
    val centerY = (minY + maxY) / 2.0
    if (!isPointInsideOrOnBoundary(centerX, centerY, polygon)) {
        return false
    }

    for (p in polygon) {
        if (p.x > minX && p.x < maxX && p.y > minY && p.y < maxY) {
            return false
        }
    }

    if (doesEdgeCutRectangle(minX, maxX, minY, maxY, polygon)) {
        return false
    }

    if (!isPointInsideOrOnBoundary(minX.toDouble(), maxY.toDouble(), polygon)) return false
    if (!isPointInsideOrOnBoundary(maxX.toDouble(), minY.toDouble(), polygon)) return false

    return true
}

fun day9Part2() {
    val coords = File("./src/main/resources/day9Input.txt")
        .readLines()
        .filter { it.isNotBlank() }
        .map { line ->
            val (x, y) = line.split(",").map { it.trim().toLong() }
            Coord(x, y)
        }

    var maxArea = 0L

    for (i in 0 until coords.size - 1) {
        for (j in i + 1 until coords.size) {
            val c1 = coords[i]
            val c2 = coords[j]

            val potentialArea = c1.areaWith(c2)

            // Optimization: Only check validity if this rectangle is bigger than best so far
            if (potentialArea > maxArea) {
                if (isValidRectangle(c1, c2, coords)) {
                    maxArea = potentialArea
                }
            }
        }
    }

    println(maxArea)
}

fun main() {
    day9Part2()
}