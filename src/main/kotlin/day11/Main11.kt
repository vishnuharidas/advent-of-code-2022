package day11


internal data class Monkey(
    val list: MutableList<Int>,
    val operation: (Int) -> Int,
    val test: (Int) -> Boolean,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspectionCount: Int = 0,
)

fun main() {

    fun part1() {

        // TODO get monkeys from file
        val monkeys = listOf(

            Monkey(
                list = mutableListOf(79, 98),
                operation = { it * 19 },
                test = { it % 23 == 0 },
                trueMonkey = 2,
                falseMonkey = 3
            ),

            Monkey(
                list = mutableListOf(54, 65, 75, 74),
                operation = { it + 6 },
                test = { it % 19 == 0 },
                trueMonkey = 2,
                falseMonkey = 0
            ),

            Monkey(
                list = mutableListOf(79, 60, 97),
                operation = { it * it },
                test = { it % 13 == 0 },
                trueMonkey = 1,
                falseMonkey = 3
            ),

            Monkey(
                list = mutableListOf(74),
                operation = { it + 3 },
                test = { it % 17 == 0 },
                trueMonkey = 0,
                falseMonkey = 1
            ),
        )

        /*println("Before starting")
        monkeys.forEachIndexed { index, monkey ->
            println("Monkey $index: ${monkey.list} Count: ${monkey.inspectionCount}")
        }*/

        repeat(20) { count ->

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

            /*println("After Round: ${count + 1}")
            monkeys.forEachIndexed { index, monkey ->
                println("Monkey $index: ${monkey.list} Count: ${monkey.inspectionCount}")
            }*/

        }

        val (i, j) = monkeys.map { it.inspectionCount }
            .sorted()
            .takeLast(2)

        println("Monkey business after 20 rounds: ${i * j}")

    }

    part1()
}