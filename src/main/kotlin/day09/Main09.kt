package day09

import getFileLines
import kotlin.math.absoluteValue

internal data class Point(var x: Int, var y: Int) {
    fun up() = y++
    fun down() = y--
    fun left() = x--
    fun right() = x++
}

fun main() {

    fun Point.nearby(other: Point) = (this.x - other.x).absoluteValue <= 1
            && (this.y - other.y).absoluteValue <= 1

    fun part1() {

        val head = Point(0, 0)
        val tail = Point(0, 0)

        val visits = mutableSetOf<Point>()

        val lines = getFileLines("day09/input.txt")

        lines
            .map { it.split(" ") }
            .map { it[0].first() to it[1].toInt() }
            .forEach { line ->

                val direction = line.first
                val steps = line.second

                repeat(steps) {

                    when (direction) {
                        'U' -> head.up()
                        'D' -> head.down()
                        'R' -> head.right()
                        'L' -> head.left()
                    }

                    // Tail follows the head
                    if (!tail.nearby(head)) {

                        when {

                            // On the same Axis
                            head.x == tail.x || head.y == tail.y -> {

                                when {
                                    // Y axis
                                    head.y > tail.y -> tail.up()
                                    head.y < tail.y -> tail.down()

                                    // X axis
                                    head.x < tail.x -> tail.left()
                                    head.x > tail.x -> tail.right()
                                }

                            }

                            // Not on axis, but on quadrants

                            // Q1
                            head.x > tail.x && head.y > tail.y -> {

                                tail.right()
                                tail.up()

                            }

                            // Q2
                            head.x < tail.x && head.y > tail.y -> {

                                tail.left()
                                tail.up()

                            }

                            // Q3
                            head.x < tail.x && head.y < tail.y -> {

                                tail.left()
                                tail.down()

                            }

                            // Q4
                            head.x > tail.x && head.y < tail.y -> {

                                tail.right()
                                tail.down()

                            }

                        }

                    }

                    visits.add(tail.copy())

                }

            }

        println("Tail visits at least once: ${visits.size}")

    }

    part1()

}