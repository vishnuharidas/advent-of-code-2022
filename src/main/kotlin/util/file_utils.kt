package util

import java.io.File

// Utility functions

fun getFileLines(name: String) = File("files/$name").readLines()

fun getFileText(name: String) = File("files/$name").readText()