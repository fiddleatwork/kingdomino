package michael.kingdomino

import org.slf4j.LoggerFactory

data class Player(val id: Int, private val strategy: Strategy) {

    private val log = LoggerFactory.getLogger(Player::class.java)

    var board: Board = Board()

    fun play(tile: Tile) {
        log.debug("Player $id is playing $tile")
        board = strategy.play(board, tile)
    }


}