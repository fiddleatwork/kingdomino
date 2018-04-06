package michael.kingdomino

class Board(val player: Int) {

    private val map = Array(9) { Array(9) { Square(SquareType.Empty, 0) } }

}