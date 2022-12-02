package day02

import getFileLines

// https://adventofcode.com/2022/day/2

fun main() {

    fun Char.canWinOver(other: Char) = this == 'R' && other == 'S'
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

            me.canWinOver(opponent) -> 6

            else -> 0

        }

        return selectionScore + outcomeScore
    }

    fun parse(line: String): Pair<Char?, Char?> {

        // To make it easy, map A/B/C/X/Y/Z to R/P/S
        val codes = mapOf(
            'A' to 'R', 'B' to 'P', 'C' to 'S',
            'X' to 'R', 'Y' to 'P', 'Z' to 'S'
        )

        val chars = line.split(" ")
            .map { it.first() }

        return Pair(codes[chars[0]], codes[chars[1]])
    }

    fun parseWithRule(line: String): Pair<Char?, Char?> {

        val round = parse(line)

        val canLoseWith = mapOf(
            'R' to 'S',
            'P' to 'R',
            'S' to 'P'
        )

        val canWithWith = mapOf(
            'R' to 'P',
            'P' to 'S',
            'S' to 'R'
        )

        return when (round.second) {
            'R' -> Pair(round.first, canLoseWith[round.first])  // R: Ensure lose
            'S' -> Pair(round.first, canWithWith[round.first])  // S: Ensure win
            else -> Pair(round.first, round.first)              // P: Ensure draw
        }

    }

    fun part1() {

        val rounds = getFileLines("day02/input.txt")
            .map { parse(it) }
            .map { getScore(it.first, it.second) }

        println("Total score without secret strategy: ${rounds.sum()}")
    }

    fun part2() {

        val rounds = getFileLines("day02/input.txt")
            .map { parseWithRule(it) }
            .map { getScore(it.first, it.second) }

        println("Total score with the secret strategy: ${rounds.sum()}")
    }

    part1()
    part2()

}