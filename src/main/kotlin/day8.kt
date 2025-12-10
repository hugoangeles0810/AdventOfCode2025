import java.io.File
import java.lang.Math.pow
import java.lang.Math.sqrt

private data class JunctionBox(
    val x: Double,
    val y: Double,
    val z: Double
) {

    fun distance(other: JunctionBox): Double {
        return sqrt(pow(other.x - x, 2.0) + pow(other.y - y, 2.0) + pow(other.z - z, 2.0))
    }
}

private data class Connection(
    val u: Int,
    val v: Int,
    val distance: Double
)

private class DSU(val size: Int) {
    private val parent = IntArray(size) { it }
    private val componentSize = IntArray(size) { 1 }

    var numComponents = size
        private set

    fun find(i: Int): Int {
        if (parent[i] != i) {
            parent[i] = find(parent[i])
        }
        return parent[i]
    }

    fun union(i: Int, j: Int): Boolean {
        val rootI = find(i)
        val rootJ = find(j)

        if (rootI != rootJ) {
            // Merge smaller component into larger one
            if (componentSize[rootI] < componentSize[rootJ]) {
                parent[rootI] = rootJ
                componentSize[rootJ] += componentSize[rootI]
            } else {
                parent[rootJ] = rootI
                componentSize[rootI] += componentSize[rootJ]
            }

            numComponents--
            return true
        }
        return false
    }

    fun getCircuitSizes(): List<Int> {
        val sizes = mutableMapOf<Int, Int>()
        for (i in 0 until size) {
            val root = find(i)
            sizes[root] = componentSize[root]
        }
        return sizes.values.toList()
    }
}

fun day8() {
    val boxes = File("./src/main/resources/day8Input.txt")
                    .readLines()
                    .map { line ->
                        val (x, y, z) = line.split(",").map { it.toDouble() }
                        JunctionBox(x, y, z)
                    }

    val connections = mutableListOf<Connection>()

    for (i in 0 until boxes.size) {
        for (j in i+1 until boxes.size) {
            val distance = boxes[i].distance(boxes[j])
            connections.add(Connection(i, j, distance))
        }
    }

    connections.sortBy { it.distance }

    val dsu = DSU(boxes.size)

    for (conn in connections) {
        val merged = dsu.union(conn.u, conn.v)

        // If they were merged, check if we are done
        if (merged) {
            if (dsu.numComponents == 1) {
                // This was the connection that united the last two pieces!
                val p1 = boxes[conn.u]
                val p2 = boxes[conn.v]

                val result = p1.x.toLong() * p2.x.toLong()

                println(result)
                return
            }
        }
    }

}

fun main() {
    day8()
}