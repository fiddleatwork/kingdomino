package michael.kingdomino

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class Game {
    private val log = LoggerFactory.getLogger(Game::class.java)

    @Autowired
    private lateinit var tileRepository: TileRepository

    private var currentTiles: MutableList<Tile> = mutableListOf()

    private var nextTiles: MutableList<Tile> = mutableListOf()

    private var players = listOf(Player(0, BestScoreStrategy()), Player(1, FirstAvailableStrategy()))

    fun start() {
        val random = Random(System.currentTimeMillis())
        var tiles = tileRepository.findAll()
        tiles = tiles.shuffled(random).subList(0, tiles.size/2).toMutableList()

        log.debug("Tiles:")
        tiles.forEach { log.debug(it.toString()) }

        dealTiles(tiles, currentTiles)
        dealTiles(tiles, nextTiles)

        val playerOrder: MutableList<Int> = mutableListOf(0,0,1,1)
        playerOrder.shuffle(random)
        log.debug("Player Order: $playerOrder")

        log.debug("Assigning players automatically, with best tile priority.")
        for(i in 3 downTo 0) {
            currentTiles[i].player = playerOrder.removeAt(0)
        }

        log.debug("Current Tiles:")
        currentTiles.forEach { log.debug(it.toString()) }

        log.debug("Starting game loop..")
        while(true) {
            while(currentTiles.size > 0) {
                val tile = currentTiles.removeAt(0)
                val player = players[tile.player]

                //log.debug("")
                //log.debug("Player $player.id board before play:")
                //log.debug(player.board.render())
                player.play(tile)
                log.debug("")
                log.debug("Player $player.id board after play:")
                log.debug(player.board.render())

                if(nextTiles.isNotEmpty()) {
                    log.debug("Player " + player.id + " is picking tile for next round..")
                    for (i in 3 downTo 0) {
                        if (nextTiles[i].player == -1) {
                            nextTiles[i].player = player.id
                            break
                        }
                    }
                }
                log.debug("Next Tiles:")
                nextTiles.forEach { log.debug(it.toString()) }
            }
            log.debug("Finished round")
            if(nextTiles.isEmpty()) {
                log.debug("No more tiles, finished.")
                break
            }
            currentTiles.addAll(nextTiles)
            nextTiles.clear()
            dealTiles(tiles, nextTiles)

            log.debug("For next round:")
            log.debug("Current Tiles:")
            currentTiles.forEach { log.debug(it.toString()) }
            log.debug("Next Tiles:")
            nextTiles.forEach { log.debug(it.toString()) }
        }
        log.debug("Game loop finished.")
        log.info("Player 0 score = " + players[0].board.score())
        log.info("Player 1 score = " + players[1].board.score())
    }

    private fun dealTiles(tiles: MutableList<Tile>, destination: MutableList<Tile>) {
        if(tiles.size == 0) {
            log.debug("No more tiles")
            return
        }
        for (i in 0..3) {
            destination.add(tiles.removeAt(0))
        }
        log.debug("Tiles:")
        destination.forEach { log.debug(it.toString()) }
    }
}