package day05

import getFileText
import java.util.*

data class Command(
    val fromStack: Int,
    val toStack: Int,
    val quantity: Int
)

fun main() {

    // FIXME parse actual stack from the input
    fun getInitialStack(fullText: String): List<Stack<Char>> {

        val stacks = listOf(
            Stack<Char>(),
            Stack<Char>(),
            Stack<Char>()
        )

        stacks[0].apply {
            push('Z')
            push('N')
        }

        stacks[1].apply {
            push('M')
            push('C')
            push('D')
        }

        stacks[2].apply {
            push('P')
        }

        return stacks

    }

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

        val stacks = getInitialStack(fullText)

        commands.forEach {

            val from = it.fromStack - 1
            val to = it.toStack - 1
            val qty = it.quantity

            repeat(qty) {
                stacks[to].push(stacks[from].pop())
            }

        }

        // Check the stack after processing all commands
        val top = stacks
            .map { it.pop() }
            .joinToString("")

        println("Top crates: $top")

    }

    part1()

}