package michael.kingdomino

class Board {

    private val map = Array(9) { Array(9) { Square(SquareType.Empty, 0) } }

    init {
        map[4][4] = Square(SquareType.Center, 0)
    }

    fun render(): String {
        var result: String = ""
        for(x in 0..8) {
            var row: String = ""
            for(y in 0..8) {
                row += "[" + map[x][y] + "]"
            }
            result += row + "\n"
        }
        return result
    }

}