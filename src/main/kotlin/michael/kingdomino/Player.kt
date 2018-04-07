package michael.kingdomino

import org.slf4j.LoggerFactory

data class Player(val id: Int) {

    private val log = LoggerFactory.getLogger(Player::class.java)

    var board: Board = Board()

    fun play(tile: Tile) {
        log.debug("Player $id is playing $tile")

        for (y in 8 downTo 0) {
            for (x in 0..8) {
// for debugging
//                if(x == 4 && y == 5) {
//                    log.debug("this spot should be valid")
//                }
                Direction.values().forEach { d ->
                    if (board.accept(tile, d, x, y)) {
                        board.place(tile, d, x, y)
                        return
                    }
                }
            }
        }
        log.debug("Could not place the tile, discarding.")
    }


}