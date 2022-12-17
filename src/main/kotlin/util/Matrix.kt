package util

data class Matrix<T>(
    val rows: Int,
    val cols: Int,
    val items: MutableList<MutableList<T>>
) {

    fun getItemAt(point: Point, default: T) =
        items.getOrElse(point.row) { null }?.getOrElse(point.col) { null } ?: default

    fun setItemAt(point: Point, item: T) {
        try {
            items.getOrElse(point.row) { null }?.set(point.col, item)
        } catch (_: Exception) {
        }

    }

    fun getItemPos(item: T): Point {

        repeat(rows) { row ->
            val col = items[row].indexOf(item)
            if (col >= 0) {
                return Point(row, col)
            }
        }

        return Point(-1, -1)
    }

    fun getAllPositionsOf(item: T): List<Point> {

        val list = mutableListOf<Point>()

        repeat(rows) { row ->
            items[row]
                .withIndex()
                .filter { it.value == item }
                .map { Point(row, it.index) }
                .forEach { list.add(it) }
        }

        return list
    }

    companion object {
        fun from(lines: List<String>) = Matrix(
            rows = lines.size,
            cols = lines.first().length,
            items = lines.map {
                it.map { item -> item }.toMutableList()
            }.toMutableList()
        )
    }

}