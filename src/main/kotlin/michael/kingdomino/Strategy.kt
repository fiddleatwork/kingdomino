package michael.kingdomino

interface Strategy {

    fun play(board: Board, tile: Tile)

}