package michael.kingdomino

import org.slf4j.LoggerFactory

class FirstAvailableStrategy : Strategy {

    private val log = LoggerFactory.getLogger(FirstAvailableStrategy::class.java)

    override fun play(board: Board, tile: Tile) : Board {
        for (y in 8 downTo 0) {
            for (x in 0..8) {
// for debugging
//                if(x == 4 && y == 5) {
//                    log.debug("this spot should be valid")
//                }
                Direction.values().forEach { d ->
                    if (board.accept(tile, d, x, y)) {
                        return board.place(tile, d, x, y)
                    }
                }
            }
        }
        log.debug("Could not place the tile, discarding.")
        return board
    }

    override fun toString(): String = this.javaClass.simpleName

}