package util

data class Point(
    val row: Int,
    val col: Int,
) {

    fun north() = Point(this.row - 1, this.col)
    fun south() = Point(this.row + 1, this.col)
    fun east() = Point(this.row, this.col + 1)
    fun west() = Point(this.row, this.col - 1)

    override fun equals(other: Any?): Boolean {
        return (other as Point).row == this.row && other.col == this.col
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }

}