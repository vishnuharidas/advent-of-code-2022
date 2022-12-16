package day11

import getFileText

internal data class Monkey(
    val list: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspectionCount: Int = 0,
)

fun main() {

    fun processMonkey(lines: List<String>): Monkey {

        val startingItems = lines[1]
            .split(":")[1]
            .split(",")
            .filter { it.isNotEmpty() }
            .map { it.trim().toLong() }

        val divisibleBy = lines[3].split("divisible by")[1].trim().toLong()

        val trueMonkey = lines[4].split("throw to monkey")[1].trim().toInt()

        val falseMonkey = lines[5].split("throw to monkey")[1].trim().toInt()

        val (op1, operation, op2) = lines[2]
            .split(" ")
            .map { it.trim() }
            .takeLast(3)

        val op = when (operation) {

            "+" -> { i: Long -> (if (op1 == "old") i else op1.toLong()) + (if (op2 == "old") i else op2.toLong()) }
            "*" -> { i: Long -> (if (op1 == "old") i else op1.toLong()) * (if (op2 == "old") i else op2.toLong()) }

            else -> error("Invalid operation")

        }

        return Monkey(
            list = startingItems.toMutableList(),
            operation = op,
            test = { it % divisibleBy == 0L },
            trueMonkey = trueMonkey,
            falseMonkey = falseMonkey
        )

    }

    fun getMonkeys(path: String) = getFileText(path)
        .split("\n\n")
        .map { it.split("\n") }
        .map { processMonkey(it) }


    fun part1() {

        val monkeys = getMonkeys("day11/input.txt")

        repeat(20) {

            monkeys.forEach { monkey ->

                if (monkey.list.isEmpty()) return@forEach // Empty monkey

                monkey.inspectionCount += monkey.list.size

                monkey.list
                    .map { monkey.operation(it) / 3 }
                    .forEach { item ->

                        // Throw to the other monkey
                        monkeys[
                            if (monkey.test(item)) monkey.trueMonkey else monkey.falseMonkey
                        ].list.add(item)
                    }

                monkey.list.clear()

            }


        }

        val (i, j) = monkeys.map { it.inspectionCount }
            .sorted().also { println(it) }
            .takeLast(2)

        println("Level of monkey business after 20 rounds: ${i * j}")

    }

    part1()
}