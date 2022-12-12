package day07

import getFileLines


internal data class File(
    val name: String,
    val isDir: Boolean,
    val fileSize: Long = 0,
    val parent: File? = null,
    val files: MutableList<File>?
) {
    val size: Long
        get() = if (isDir) {
            files?.sumOf { it.size } ?: 0
        } else {
            this.fileSize
        }
}

fun main() {

    fun printFileTree(file: File, level: Int) {
        println("")
        repeat(level) { print(" ") }
        print("- ${file.name} ${if (file.isDir) "(dir," else "(file,"} size=${file.size})")

        file.files?.forEach {
            printFileTree(it, level + 1)
        }
    }

    fun findDirsHavingAtMost(size: Long, root: File): List<File> {

        root.files ?: return emptyList()

        val list = mutableListOf<File>()

        list.addAll(root.files.filter { it.isDir && it.size < size })

        return list + root.files
            .filter { it.isDir }
            .map { file -> findDirsHavingAtMost(size, file) }
            .flatten()

    }

    fun findAllDirs(root: File): List<File> {

        root.files ?: return emptyList()

        return root.files.filter { it.isDir } + root.files.map { findAllDirs(it) }.flatten()
    }

    fun parseFile(line: String, parent: File): File {

        val tokens = line.split(" ")

        val isDir = tokens[0].first() == 'd'

        return File(
            name = tokens[1],
            isDir = isDir,
            fileSize = if (!isDir) tokens[0].toLong() else 0L,
            parent = parent,
            files = if (isDir) mutableListOf() else null
        )

    }

    fun part1() {

        val root = File(
            name = "/",
            isDir = true,
            files = mutableListOf(),
        )

        var current = root

        val lines = getFileLines("day07/input.txt")

        lines.forEach {

            // Process command - move around dirs
            if (it.startsWith("$")) {

                val command = it.split(" ")[1]

                if (command == "cd") {

                    when (val path = it.split(" ")[2]) {

                        // Move to root dir
                        "/" -> {

                            current = root

                        }

                        // Move to parent dir
                        ".." -> {

                            current
                                .parent
                                ?.let { parent ->
                                    current = parent
                                }
                        }

                        // Move to child dir
                        else -> {

                            current
                                .files
                                ?.find { file -> file.name == path }
                                ?.let { file ->
                                    current = file
                                }
                        }
                    }
                }

            } else { // Process files and add to the current directory

                current.files?.add(parseFile(it, current))

            }

        }

        // printFileTree(root, 0)

        // Find all the directories with a total size of at most 100000.
        val dirsWith1000L = findDirsHavingAtMost(size = 100000L, root)

        println("Sum of all dirs with at most 100000: ${dirsWith1000L.sumOf { it.size }}")

    }

    fun part2() {

        val root = File(
            name = "/",
            isDir = true,
            files = mutableListOf(),
        )

        var current = root

        val lines = getFileLines("day07/input.txt")

        lines.forEach {

            // Process command - move around dirs
            if (it.startsWith("$")) {

                val command = it.split(" ")[1]

                if (command == "cd") {

                    when (val path = it.split(" ")[2]) {

                        // Move to root dir
                        "/" -> {

                            current = root

                        }

                        // Move to parent dir
                        ".." -> {

                            current
                                .parent
                                ?.let { parent ->
                                    current = parent
                                }
                        }

                        // Move to child dir
                        else -> {

                            current
                                .files
                                ?.find { file -> file.name == path }
                                ?.let { file ->
                                    current = file
                                }
                        }
                    }
                }

            } else { // Process files and add to the current directory

                current.files?.add(parseFile(it, current))

            }

        }

        //printFileTree(root, 0)

        val allDirs = findAllDirs(root) + root

        val totalDiskSize = 70_000_000L
        val requiredSizeForUpdate = 30_000_000L

        val availableSize = totalDiskSize - root.size
        val extraNeeded = requiredSizeForUpdate - availableSize

        val dirToDelete = allDirs.filter { it.size > extraNeeded }.minByOrNull { it.size }

        println("Dir to delete: ${dirToDelete?.name}, size: ${dirToDelete?.size}")

    }

    //part1()

    part2()
}