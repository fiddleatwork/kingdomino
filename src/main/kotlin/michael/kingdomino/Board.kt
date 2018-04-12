package michael.kingdomino

import org.slf4j.LoggerFactory

data class Board(private val map: BoardMap = BoardMap()) {

    private val log = LoggerFactory.getLogger(Board::class.java)

    fun deepCopy(): Board {
        return Board(map.deepCopy())
    }

    fun render(): String {
        var result = "\n  " + renderXAxis() + "\n"
        for (y in 8 downTo 0) {
            var row = "$y "
            for (x in 0..8) {
                if (map.square(x,y).type == SquareType.Empty) {
                    row += "[   ]"
                } else {
                    row += "[" + map.square(x,y) + "]"
                }
            }
            result += row + "\n"
        }
        result += "  " + renderXAxis() + "\n"
        return result
    }

    private fun renderXAxis(): String {
        var result = ""
        for(i in 0..8) {
            result += "  $i  "
        }
        return result
    }

    fun accept(tile: Tile, direction: Direction, x: Int, y: Int): Boolean {
        val x2 = x + direction.secondSquareOffsetX
        val y2 = y + direction.secondSquareOffsetY
        if (!inRange(x, y) || !inRange(x2, y2)) {
            //log.debug("Tile $tile in $direction at ($x,$y) is out of range (off the board)")
            return false
        }
        if (map.square(x,y).type != SquareType.Empty || map.square(x2,y2).type != SquareType.Empty) {
            //log.debug("Tile $tile in $direction at ($x,$y) is not on an empty square.")
            return false
        }
        if (neighborsOf(x, y).filter { it.type == SquareType.Center || it.type == tile.squares[0].type }.isEmpty() &&
                neighborsOf(x2, y2).filter { it.type == SquareType.Center || it.type == tile.squares[1].type }.isEmpty()) {
            //log.debug("Tile $tile in $direction at ($x,$y) does not have a compatible neighbor")
            return false
        }
        if(Math.max(maxX(), Math.max(x, x2)) - Math.min(minX(), Math.min(x, x2)) + 1 > 5 ||
                Math.max(maxY(), Math.max(y, y2)) - Math.min(minY(), Math.min(y, y2)) + 1 > 5) {
           // log.debug("Tile $tile in $direction at ($x,$y) would be out of bounds")
            return false
        }

        //log.debug("Tile $tile in $direction at ($x,$y) is valid")
        return true
    }

    private fun minX(): Int {
        for (x in 0..8) {
            for (y in 0..8) {
                if (map.square(x,y).type != SquareType.Empty) {
                    return x
                }
            }
        }
        throw IllegalStateException("Should never happen")
    }

    private fun maxX(): Int {
        for (x in 8 downTo 0) {
            for (y in 0..8) {
                if (map.square(x,y).type != SquareType.Empty) {
                    return x
                }
            }
        }
        throw IllegalStateException("Should never happen")
    }

    private fun minY(): Int {
        for (y in 0..8) {
            for (x in 0..8) {
                if (map.square(x,y).type != SquareType.Empty) {
                    return y
                }
            }
        }
        throw IllegalStateException("Should never happen")
    }
    private fun maxY(): Int {
        for (y in 8 downTo 0) {
            for (x in 0..8) {
                if (map.square(x,y).type != SquareType.Empty) {
                    return y
                }
            }
        }
        throw IllegalStateException("Should never happen")
    }

    private fun neighborsOf(x: Int, y: Int): List<Square> {
        val neighbors: MutableList<Square> = mutableListOf()
        if (inRange(x - 1, y)) {
            neighbors.add(map.square(x - 1,y))
        }
        if (inRange(x + 1, y)) {
            neighbors.add(map.square(x + 1,y))
        }
        if (inRange(x, y - 1)) {
            neighbors.add(map.square(x,y - 1))
        }
        if (inRange(x, y + 1)) {
            neighbors.add(map.square(x,y + 1))
        }
        return neighbors
    }


    private fun inRange(x: Int, y: Int) = x in 0..8 && y in 0..8

    fun place(tile: Tile, direction: Direction, x: Int, y: Int): Board {
        if (accept(tile, direction, x, y)) {
            log.debug("Placing $tile in $direction at ($x,$y)")
            //log.error("b4 place " + render())
            val x2 = x + direction.secondSquareOffsetX
            val y2 = y + direction.secondSquareOffsetY

            //var newTile = tile.copy(squares = listOf(tile.squares[0].copy(coordinates = Coordinates(x,y)), tile.squares[1].copy()))
            //tile.squares[0] = tile.squares[0].copy(coordinates = Coordinates(x,y))
            //tile.squares[0].coordinates = Coordinates(x,y)
            val newSquare1 = tile.squares[0].copy(coordinates = Coordinates(x,y))
            val newMapWithFirstSquare = map.withSquare(x,y,newSquare1)

//            tile.squares[1].x = x2
//            tile.squares[1].y = y2
            val newSquare2 = tile.squares[1].copy(coordinates = Coordinates(x2,y2))
            val newMapWithSecondSquare = newMapWithFirstSquare.withSquare(x2,y2,newSquare2)
            return Board(newMapWithSecondSquare)
        } else {
            log.debug("Cannot place $tile in $direction at ($x,$y)\") because it's not a valid move")
            throw IllegalArgumentException("Cannot place $tile in $direction at ($x,$y)\") because it's not a valid move")
        }
    }


    fun score(): Int {
        val counted = Array(9) { Array(9) { false} }
        var totalScore = 0
        for(y in 8 downTo 0) {
            for (x in 0..8) {
                if(map.square(x,y).type == SquareType.Empty) {
                    continue
                }
                if(counted[x][y]) {
                    continue
                }
                val points = PointCount(0,0)
                score(counted, map.square(x,y).type, Coordinates(x, y), points)
                totalScore += points.total()
            }
        }
        return totalScore
    }

    private data class PointCount(var tiles: Int, var crowns: Int) {
        fun addTile() {
            tiles++
        }

        fun addCrowns(crowns: Int) {
            this.crowns += crowns
        }

        fun total() : Int = tiles*crowns
    }

    private fun score(counted: Array<Array<Boolean>>, squareType: SquareType, coordinates: Coordinates, pointCount: PointCount) {
        pointCount.addTile()
        pointCount.addCrowns(map.square(coordinates.x,coordinates.y).crowns)
        counted[coordinates.x][coordinates.y] = true
        val neighbors = neighborsOf(coordinates.x,coordinates.y).filter{it.type == squareType && !counted[it.coordinates.x][it.coordinates.y]}
        neighbors.forEach{score(counted, squareType, it.coordinates, pointCount)}
    }

    fun squareAt(x: Int, y: Int): Square {
        return map.square(x,y)
    }
}