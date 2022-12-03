package day03

import getFileLines

// https://adventofcode.com/2022/day/3
fun main() {


    fun part1() {

        val priorities = getFileLines("day03/input.txt")
            .map { it.chunked(it.length / 2) }
            .map { it[0] to it[1] }
            .map { it.first.toList().intersect(it.second.toList().toSet()) }
            .map {
                when {
                    it.first().isLowerCase() -> it.first() - 'a' + 1
                    it.first().isUpperCase() -> it.first() - 'A' + 27
                    else -> error("Invalid item $it")
                }
            }

        println("Sum of priorities: ${priorities.sum()}")
    }

    fun part2() {

        val priorities = getFileLines("day03/input.txt")
            .chunked(3)
            .map {
                it[0].toList().intersect(it[1].toList().intersect(it[2].toSet()))
            }
            .map {
                when {
                    it.first().isLowerCase() -> it.first() - 'a' + 1
                    it.first().isUpperCase() -> it.first() - 'A' + 27
                    else -> error("Invalid item $it")
                }
            }

        println("Sum of priorities of badges: ${priorities.sum()}")

    }

    part1()
    part2()

}