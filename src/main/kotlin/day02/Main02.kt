package day02

import util.getFileLines

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
            'S' -> 3    // Scissor
            else -> error("Invalid shape")
        }

        val outcomeScore = when {

            opponent == me -> 3

            me.canWinOver(opponent) -> 6

            else -> 0

        }

        return selectionScore + outcomeScore
    }

    fun parse(line: String): Pair<Char?, Char?> {

        val chars = line.split(" ")
            .map { it.first() }
            .map {

                // To make it easy, map A/B/C/X/Y/Z to R/P/S
                when (it) {
                    'A', 'X' -> 'R'
                    'B', 'Y' -> 'P'
                    'C', 'Z' -> 'S'
                    else -> error("Invalid shape: $it")
                }
            }

        return Pair(chars[0], chars[1])
    }

    fun parseWithRule(line: String): Pair<Char?, Char?> {

        val round = parse(line)

        val loserOf = mapOf(
            'R' to 'S',
            'P' to 'R',
            'S' to 'P'
        )

        val winnerOf = mapOf(
            'R' to 'P',
            'P' to 'S',
            'S' to 'R'
        )

        return when (round.second) {
            'R' -> Pair(round.first, loserOf[round.first])      // R: Ensure lose
            'S' -> Pair(round.first, winnerOf[round.first])     // S: Ensure win
            'P' -> Pair(round.first, round.first)               // P: Ensure draw
            else -> error("Invalid shape ${round.second}")
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