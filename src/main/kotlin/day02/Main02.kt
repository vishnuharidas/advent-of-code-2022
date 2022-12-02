package day02

import getFileLines

// https://adventofcode.com/2022/day/2


fun main() {

    fun Char.canWin(other: Char) = this == 'R' && other == 'S'
            || this == 'P' && other == 'R'
            || this == 'S' && other == 'P'

    fun getScore(opponent: Char?, me: Char?): Int {

        opponent ?: return 0
        me ?: return 0

        if (opponent == ' ' || me == ' ') return 0

        val selectionScore = when (me) {
            'R' -> 1    // Rock
            'P' -> 2    // Paper
            else -> 3   // Scissor
        }

        val outcomeScore = when {

            opponent == me -> 3

            me.canWin(opponent) -> 6

            else -> 0

        }

        return selectionScore + outcomeScore
    }

    fun parse(line: String): Pair<Char?, Char?> {

        // To make it easy, map A/B/C/X/Y/Z to R/P/S
        val codes = mapOf(
            'A' to 'R',
            'B' to 'P',
            'C' to 'S',
            'X' to 'R',
            'Y' to 'P',
            'Z' to 'S'
        )

        val chars = line.split(" ")
            .map { it.first() }

        return Pair(codes[chars[0]], codes[chars[1]])
    }

    val rounds = getFileLines("day02/input.txt")
        .map { parse(it) }
        .map { getScore(it.first, it.second) }

    println("Total score: ${rounds.sum()}")
}