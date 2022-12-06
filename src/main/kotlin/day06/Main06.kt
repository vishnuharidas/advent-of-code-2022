package day06

import getFileText

fun main() {

    fun process(message: String, distinctSize: Int = 4): Int {

        val list = mutableListOf<Char>()

        // Add first N characters, to avoid redundant `if(size==N)` check in the loop.
        list.addAll(
            message.take(distinctSize).toList()
        )

        message
            .drop(distinctSize) // These were already added
            .forEachIndexed { index, char ->

                if (list.distinct().size == distinctSize) {
                    return index + distinctSize
                } else {
                    list.removeFirst()
                    list.add(char)
                }

            }

        return -1

    }

    fun part1() {

        val markerIndex = process(getFileText("day06/input.txt"))

        println("Marker index: $markerIndex")

    }

    fun part2() {

        val messageIndex = process(getFileText("day06/input.txt"), distinctSize = 14)

        println("Message index: $messageIndex")

    }

    part1()
    part2()
}