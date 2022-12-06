package day06

import getFileText

fun main() {

    fun process(message: String): Int {

        val markerSize = 4

        val list = mutableListOf<Char>()

        // Add first 4 characters, to avoid redundant `if(size==4)` check in the loop.
        list.addAll(
            message.take(markerSize).toList()
        )

        message
            .drop(markerSize) // These were already added
            .forEachIndexed { index, char ->

                if (list.distinct().size == markerSize) {
                    return index + markerSize
                } else {
                    list.removeFirst()
                    list.add(char)
                }

            }

        return -1

    }

    fun part1() {

        val markerIndex = process(getFileText("day06/input.txt"))

        println("Marker starts at: $markerIndex")

    }

    part1()
}