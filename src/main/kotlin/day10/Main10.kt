package day10

import getFileLines
import kotlin.math.absoluteValue

fun main() {

    fun part1() {

        val lines = getFileLines("day10/input.txt")

        val signalStrengths = mutableListOf<Int>()
        val strengthCycles = listOf(20, 60, 100, 140, 180, 220)

        lines
            .map {

                if (it.startsWith("addx")) {
                    listOf(0, it.split(" ")[1].toInt()) // Flatten by adding one more `noop` in front
                } else {
                    listOf(0) // `noop`
                }

            }
            .flatten()
            .foldIndexed(1) { index, acc, item ->

                if (index in strengthCycles) {
                    signalStrengths.add(index * acc)
                }

                acc + item

            }

        println("Sum of signal strengths: ${signalStrengths.sum()}")

    }

    fun part2() {

        val lines = getFileLines("day10/input.txt")

        val sprite = "###........................................".map { it }.toMutableList()

        lines
            .map {

                if (it.startsWith("addx")) {
                    listOf(0, it.split(" ")[1].toInt()) // Flatten by adding one more `noop` in front
                } else {
                    listOf(0) // `noop`
                }

            }
            .flatten()
            .foldIndexed(1) { index, acc, item ->

                val pixel = (acc % 40).absoluteValue.coerceIn(1..38)
                sprite.replaceAll { '.' }
                sprite[pixel - 1] = '#'
                sprite[pixel] = '#'
                sprite[pixel + 1] = '#'

                if (index % 40 == 0) println()

                print(sprite[index % 40])

                acc + item
            }


    }

    part1()

    part2()

}