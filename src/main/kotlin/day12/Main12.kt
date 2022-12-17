package day12

import getFileLines

internal data class Matrix<T>(
    val rows: Int,
    val cols: Int,
    val items: MutableList<MutableList<T>>
) {

    fun getItemAt(point: Point, default: T) =
        items.getOrElse(point.row) { null }?.getOrElse(point.col) { null } ?: default

    fun setItemAt(point: Point, char: T) {
        try {
            items.getOrElse(point.row) { null }?.set(point.col, char)
        } catch (_: Exception) {
        }

    }

    fun getItemPos(char: T): Point {

        repeat(rows) { row ->
            val col = items[row].indexOf(char)
            if (col >= 0) {
                return Point(row, col)
            }
        }

        return Point(-1, -1)
    }

    companion object {
        fun from(lines: List<String>) = Matrix(
            rows = lines.size,
            cols = lines.first().length,
            items = lines.map {
                it.map { char -> char }.toMutableList()
            }.toMutableList()
        )
    }

}

internal data class Point(
    val row: Int,
    val col: Int,
) {

    fun north() = Point(this.row - 1, this.col)
    fun south() = Point(this.row + 1, this.col)
    fun east() = Point(this.row, this.col + 1)
    fun west() = Point(this.row, this.col - 1)

    override fun equals(other: Any?): Boolean {
        return (other as Point).row == this.row && other.col == this.col
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }

}

fun main() {

    fun bfs(matrix: Matrix<Char>, startingPoint: Point, endingPoint: Point): Int {

        /* For visualization purpose
        val visited = Matrix(
            rows = matrix.rows,
            cols = matrix.cols,
            items = MutableList(matrix.rows) {
                MutableList(matrix.cols) { -1 }
            }
        )
         */

        val queue = mutableListOf<Point>().apply {
            add(startingPoint)
        }

        val distanceMap = mutableMapOf<Point, Int>().apply {
            this[startingPoint] = 0
        }

        while (queue.isNotEmpty()) {

            val pt = queue.removeFirst()

            // Neighbors - from all sides, then filter them
            listOf(pt.east(), pt.west(), pt.south(), pt.north())
                .filter { it.col in matrix.items[0].indices && it.row in matrix.items.indices }
                .filter { !distanceMap.contains(it) }
                // This is *THE* rule for neighbor that I misinterpreted. Spent 2 days on this and realized the mistake from Reddit: https://www.reddit.com/r/adventofcode/search/?q=day%2012
                .filter { matrix.getItemAt(it, 'M') <= matrix.getItemAt(pt, 'W') + 1 }
                .forEach {
                    distanceMap[it] = distanceMap[pt]!! + 1
                    queue.add(it)
                }

        }

        /* Visualization
        distanceMap.forEach { (k, v) -> visited.setItemAt(k, v) }
        matrix.items.forEach { println(it.joinToString("\t")) }
        println()
        visited.items.forEach { println(it.joinToString("\t")) }
        */

        return distanceMap[endingPoint]!!

    }

    fun part1() {

        val matrix = Matrix.from(getFileLines("day12/input.txt"))

        val startingPoint = matrix.getItemPos('S')
        val endingPoint = matrix.getItemPos('E')

        matrix.setItemAt(startingPoint, 'a')
        matrix.setItemAt(endingPoint, 'z')

        val steps = bfs(matrix, startingPoint, endingPoint)

        println("Total steps from S to E: $steps")

    }

    part1()

}