package day11

import util.getFileText

internal data class Monkey(
    val list: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val divisibleBy: Long,
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

        val (operation, op2) = lines[2]
            .split(" ")
            .map { it.trim() }
            .takeLast(2)

        val op = when (operation) {

            "+" -> { i: Long -> i + (if (op2 == "old") i else op2.toLong()) }
            "*" -> { i: Long -> i * (if (op2 == "old") i else op2.toLong()) }

            else -> error("Invalid operation")

        }

        return Monkey(
            list = startingItems.toMutableList(),
            operation = op,
            test = { it % divisibleBy == 0L },
            divisibleBy = divisibleBy,
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
            .sorted()
            .takeLast(2)

        println("Level of monkey business after 20 rounds: ${i * j}")

    }

    fun part2() {

        val monkeys = getMonkeys("day11/input.txt")

        val newDivisor = monkeys.map { it.divisibleBy }.reduce { acc, l -> acc * l }

        repeat(10_000) { r ->

            monkeys.forEach { monkey ->

                if (monkey.list.isEmpty()) return@forEach // Empty monkey

                monkey.inspectionCount += monkey.list.size

                monkey.list
                    .map { monkey.operation(it) % newDivisor }
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
            .sorted()
            .map { it.toLong() }
            .takeLast(2)

        println("Level of monkey business after 10000 rounds (w/o division by 3, and with new logic): ${i * j}")

    }

    part1()

    part2()
}