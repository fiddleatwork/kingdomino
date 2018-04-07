package michael.kingdomino

import org.slf4j.LoggerFactory

data class Player(val id: Int) {

    private val log = LoggerFactory.getLogger(Player::class.java)

    private var board: Board = Board()

    fun play(tile: Tile) {
        log.debug("Player $id is playing $tile")
    }


}