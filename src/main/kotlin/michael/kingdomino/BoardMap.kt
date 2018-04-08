package michael.kingdomino

import org.slf4j.LoggerFactory
import java.util.*

data class BoardMap(private val map: Array<Array<Square>>) {

    private val log = LoggerFactory.getLogger(BoardMap::class.java)

    constructor() : this(Array(9) { Array(9) { Square(SquareType.Empty, 0) } }) {
        map[4][4] = Square(SquareType.Center, 0)
    }

    fun withSquare(x: Int, y:Int, square: Square) : BoardMap {
        val newMap = map.copyOf()
        newMap[x][y] = square
        return BoardMap(newMap)
    }

    fun square(x: Int, y: Int) : Square {
        return map[x][y]
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BoardMap

        if (!Arrays.equals(map, other.map)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(map)
    }
}

