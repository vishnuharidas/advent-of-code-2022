package day05

import getFileText

data class Command(
    val fromStack: Int,
    val toStack: Int,
    val quantity: Int
)

fun main() {

    /*fun getInitialStack(fullText: String): ElvesStack {

        val stackSeq = fullText.split("\n\n")[0]

    }*/

    fun getCommands(fullText: String): List<Command> {

        return fullText.split("\n\n")[1]
            .lines()
            .filterNot { it.trim().isEmpty() }
            .map { it ->
                it.replace("move ", "")
                    .replace(" from ", ",")
                    .replace(" to ", ",")
                    .split(",")
                    .map { n -> n.toInt() }
            }
            .map {
                Command(
                    fromStack = it[1],
                    toStack = it[2],
                    quantity = it[0]
                )
            }

    }

    fun part1() {

        val fullText = getFileText("day05/input_test.txt")

        val commands = getCommands(fullText)

        println("Commands: $commands")

    }

    part1()

}