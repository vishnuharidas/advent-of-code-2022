package day05

import util.getFileText
import java.util.*

data class Command(
    val fromStack: Int,
    val toStack: Int,
    val quantity: Int
)

fun main() {

    fun getInitialStack(fullText: String): List<Stack<Char>> {

        val stacks = listOf(
            Stack<Char>(),
            Stack<Char>(),
            Stack<Char>(),
            Stack<Char>(),
            Stack<Char>(),
            Stack<Char>(),
            Stack<Char>(),
            Stack<Char>(),
            Stack<Char>(),
        )

        // Indices of characters in the input.txt file
        val indices = listOf(1, 5, 9, 13, 17, 21, 25, 29, 33)

        fullText.split("\n\n")[0]
            .lines()
            .reversed()
            .drop(1) // The numbers row
            .forEach { line ->

                indices.forEachIndexed { index, i ->
                    if (line.length > i && line[i].isLetter()) {
                        stacks[index].push(line[i])
                    }
                }

            }

        return stacks

    }

    fun getCommands(fullText: String): List<Command> {

        return fullText.split("\n\n")[1]
            .lines()
            .filterNot { it.trim().isEmpty() }
            .map {

                // Format: "move 2 from 7 to 2"
                // Indexes:   0  1   2  3  4 5
                val command = it.split(" ")

                Command(
                    fromStack = command[3].toInt(),
                    toStack = command[5].toInt(),
                    quantity = command[1].toInt()
                )

            }

    }

    fun part1() {

        val fullText = getFileText("day05/input.txt")

        val commands = getCommands(fullText)

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
            .filterNot { it.isEmpty() }
            .map { it.peek() }
            .joinToString("")

        println("Top crates: $top")

    }

    fun part2() {

        val fullText = getFileText("day05/input.txt")

        val commands = getCommands(fullText)

        val stacks = getInitialStack(fullText)

        commands.forEach {

            val from = it.fromStack - 1
            val to = it.toStack - 1
            val qty = it.quantity

            // Pop qty items, then reverse, then push to destination
            (1..qty)
                .map { stacks[from].pop() }
                .reversed()
                .map { char -> stacks[to].push(char) }

        }

        // Check the stack after processing all commands
        val top = stacks
            .filterNot { it.isEmpty() }
            .map { it.peek() }
            .joinToString("")

        println("Top crates with the new CrateMover9001: $top")

    }

    part1()
    part2()

}