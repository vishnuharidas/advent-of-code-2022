package day12

import getFileLines
import util.Matrix
import util.Point

fun main() {

    fun bfs(matrix: Matrix<Char>, startingPoint: Point, endingPoint: Point): Int? {

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

        return distanceMap[endingPoint]

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

    fun part2() {

        val matrix = Matrix.from(getFileLines("day12/input.txt"))

        val endingPoint = matrix.getItemPos('E')

        matrix.setItemAt(matrix.getItemPos('S'), 'a')
        matrix.setItemAt(endingPoint, 'z')

        val minDistance = matrix.getAllPositionsOf('a')
            .mapNotNull { bfs(matrix, it, endingPoint) }
            .min()

        println("Minimum distance: $minDistance")

    }

    part1()

    part2()

}