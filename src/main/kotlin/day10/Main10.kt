package day10

import getFileLines

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

    part1()

}