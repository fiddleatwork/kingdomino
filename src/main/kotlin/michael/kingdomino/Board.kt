package michael.kingdomino

class Board {

    private val map = Array(9) { Array(9) { Square(SquareType.Empty, 0) } }

    init {
        map[4][4] = Square(SquareType.Center, 0)
    }
}