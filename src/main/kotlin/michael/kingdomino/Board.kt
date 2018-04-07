package michael.kingdomino

import org.slf4j.LoggerFactory

class Board {

    private val log = LoggerFactory.getLogger(Board::class.java)

    private val map = Array(9) { Array(9) { Square(SquareType.Empty, 0) } }

    init {
        map[4][4] = Square(SquareType.Center, 0)
    }

    fun render(): String {
        var result = "\n"
        for (y in 8 downTo 0) {
            var row = ""
            for (x in 0..8) {
                if (map[x][y].type == SquareType.Empty) {
                    row += "[   ]"
                } else {
                    row += "[" + map[x][y] + "]"
                }
            }
            result += row + "\n"
        }
        return result
    }

    fun accept(tile: Tile, direction: Direction, x: Int, y: Int): Boolean {
        val x2 = x + direction.secondSquareOffsetX
        val y2 = y + direction.secondSquareOffsetY
        if (!inRange(x, y) || !inRange(x2, y2)) {
            log.debug("Tile $tile in $direction at ($x,$y) is out of range (off the board)")
            return false
        }
        if (map[x][y].type != SquareType.Empty || map[x2][y2].type != SquareType.Empty) {
            log.debug("Tile $tile in $direction at ($x,$y) is not on an empty square.")
            return false
        }
        if (neighborsOf(x, y).filter { it.type == SquareType.Center || it.type == tile.squares[0].type }.isEmpty() &&
                neighborsOf(x2, y2).filter { it.type == SquareType.Center || it.type == tile.squares[1].type }.isEmpty()) {
            log.debug("Tile $tile in $direction at ($x,$y) does not have a compatible neighbor")
            return false
        }
        if(Math.max(maxX(), Math.max(x, x2)) - Math.min(minX(), Math.min(x, x2)) + 1 > 5 ||
                Math.max(maxY(), Math.max(y, y2)) - Math.min(minY(), Math.min(y, y2)) + 1 > 5) {
            log.debug("Tile $tile in $direction at ($x,$y) would be out of bounds")
            return false
        }

        log.debug("Tile $tile in $direction at ($x,$y) is valid")
        return true
    }

    private fun minX(): Int {
        for (x in 0..8) {
            for (y in 0..8) {
                if (map[x][y].type != SquareType.Empty) {
                    return x
                }
            }
        }
        throw IllegalStateException("Should never happen")
    }

    private fun maxX(): Int {
        for (x in 8 downTo 0) {
            for (y in 0..8) {
                if (map[x][y].type != SquareType.Empty) {
                    return x
                }
            }
        }
        throw IllegalStateException("Should never happen")
    }

    private fun minY(): Int {
        for (y in 0..8) {
            for (x in 0..8) {
                if (map[x][y].type != SquareType.Empty) {
                    return y
                }
            }
        }
        throw IllegalStateException("Should never happen")
    }
    private fun maxY(): Int {
        for (y in 8 downTo 0) {
            for (x in 0..8) {
                if (map[x][y].type != SquareType.Empty) {
                    return y
                }
            }
        }
        throw IllegalStateException("Should never happen")
    }

    private fun neighborsOf(x: Int, y: Int): List<Square> {
        val neighbors: MutableList<Square> = mutableListOf()
        if (inRange(x - 1, y)) {
            neighbors.add(map[x - 1][y])
        }
        if (inRange(x + 1, y)) {
            neighbors.add(map[x + 1][y])
        }
        if (inRange(x, y - 1)) {
            neighbors.add(map[x][y - 1])
        }
        if (inRange(x, y + 1)) {
            neighbors.add(map[x][y + 1])
        }
        return neighbors
    }


    private fun inRange(x: Int, y: Int) = x in 0..8 && y in 0..8

    fun place(tile: Tile, direction: Direction, x: Int, y: Int) {
        if (accept(tile, direction, x, y)) {
            log.debug("Placing $tile in $direction at ($x,$y)")
            val x2 = x + direction.secondSquareOffsetX
            val y2 = y + direction.secondSquareOffsetY
            map[x][y] = tile.squares[0]
            map[x2][y2] = tile.squares[1]
        } else {
            log.debug("Cannot place $tile in $direction at ($x,$y)\") because it's not a valid move")
        }
    }

    fun score(): Int {
        return 0
    }
}