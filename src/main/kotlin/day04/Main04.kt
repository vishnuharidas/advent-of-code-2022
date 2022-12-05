package day04

import getFileLines

fun main() {

    fun part1() {

        val count = getFileLines("day04/input.txt")
            .map {
                val sections = it.split(",")
                val section1 = sections[0].split("-")[0].toInt().rangeTo(sections[0].split("-")[1].toInt())
                val section2 = sections[1].split("-")[0].toInt().rangeTo(sections[1].split("-")[1].toInt())
                section1 to section2
            }
            .count {
                val section1 = it.first
                val section2 = it.second

                section1.first in section2 && section1.last in section2     // Section 1 in section 2?
                        || section2.first in section1 && section2.last in section1 // Section 2 in section 1?
            }

        println("Count of pairs with overlaps: $count")

    }

    fun part2() {

        val count = getFileLines("day04/input.txt")
            .map {
                val sections = it.split(",")
                val section1 = sections[0].split("-")[0].toInt().rangeTo(sections[0].split("-")[1].toInt())
                val section2 = sections[1].split("-")[0].toInt().rangeTo(sections[1].split("-")[1].toInt())
                section1 to section2
            }
            .count {

                (it.first intersect it.second).isNotEmpty()

            }

        println("Count of pairs with ranges overlap: $count")

    }

    part1()
    part2()

}