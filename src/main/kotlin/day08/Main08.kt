package day08

import util.getFileLines

internal data class Tree(
    val height: Int,
    val north: List<Int>,
    val south: List<Int>,
    val east: List<Int>,
    val west: List<Int>
)

internal enum class DIRECTION { NORTH, EAST, WEST, SOUTH }

fun main() {

    fun getTree(lines: List<String>, x: Int, y: Int): Tree {

        val row = lines[y].map { it.digitToInt() }
        val col = lines.map { it[x] }.map { it.digitToInt() }

        return Tree(
            height = row[x],
            north = col.subList(0, y + 1),
            south = col.subList(y, col.size),
            east = row.subList(0, x + 1),
            west = row.subList(x, row.size)
        )

    }

    fun isVisible(list: List<Int>, fromDirection: DIRECTION): Boolean {

        val item = when (fromDirection) {
            DIRECTION.NORTH, DIRECTION.EAST -> list[list.size - 1]
            DIRECTION.WEST, DIRECTION.SOUTH -> list[0]
        }

        return list.max() == item
                && list.count { it == item } == 1

    }

    fun Tree.isVisible(): Boolean = isVisible(this.east, DIRECTION.EAST)
            || isVisible(this.west, DIRECTION.WEST)
            || isVisible(this.north, DIRECTION.NORTH)
            || isVisible(this.south, DIRECTION.SOUTH)

    fun Tree.scenicScore(): Int {

        var northScore = 0
        var southScore = 0
        var eastScore = 0
        var westScore = 0

        for (otherTree in this.north.reversed().drop(1)) {
            northScore++
            if (otherTree >= this.height) {
                break
            }
        }

        for (otherTree in this.south.drop(1)) {
            southScore++
            if (otherTree >= this.height) {
                break
            }
        }

        for (otherTree in this.east.reversed().drop(1)) {
            eastScore++
            if (otherTree >= this.height) {
                break
            }
        }

        for (otherTree in this.west.drop(1)) {
            westScore++
            if (otherTree >= this.height) {
                break
            }
        }

        return northScore * southScore * eastScore * westScore

    }

    fun part1() {

        val lines = getFileLines("day08/input.txt")

        val trees = mutableListOf<Tree>()

        val maxCol = lines[1].length
        val maxRow = lines.size

        repeat(maxCol) { x ->
            repeat(maxRow) { y ->

                trees.add(
                    getTree(lines, x, y)
                )

            }
        }

        val visibleTrees = trees.count { it.isVisible() }

        println("Visible trees from outside: $visibleTrees")

    }

    fun part2() {

        val lines = getFileLines("day08/input.txt")

        val trees = mutableListOf<Tree>()

        val maxCol = lines[1].length
        val maxRow = lines.size

        repeat(maxCol) { x ->
            repeat(maxRow) { y ->

                trees.add(
                    getTree(lines, x, y)
                )

            }
        }

        println("Highest scenic score: ${trees.maxOf { it.scenicScore() }}")

    }

    part1()

    part2()

}