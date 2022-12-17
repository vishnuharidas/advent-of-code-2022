package day01

import util.getFileLines

// https://adventofcode.com/2022/day/1

fun main() {

    var max = 0
    var sum = 0

    var sums = mutableListOf<Int>()

    getFileLines("day01/input.txt").forEach {

        if (it.isEmpty()) { // Empty line? Next Elf.

            sums.add(sum)
            sum = 0

        } else {

            sum += it.toInt()

            if (sum > max) {
                max = sum
            }

        }
    }


    println("Max calories: $max")
    println("Sum of top 3 calories: ${sums.sortedDescending().take(3).sum()}")

}