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

    private var player1: Player = Player(1)

    private var player2: Player = Player(2)

    fun start() {
        val allTiles = tileRepository.findAll()
        val random = Random(System.currentTimeMillis())
        Collections.shuffle(allTiles, random)
        val tiles = allTiles.subList(0, allTiles.size/2).toMutableList()

        log.debug("Tiles:")
        tiles.forEach { log.debug(it.toString()) }

        dealTiles(tiles, currentTiles)
        dealTiles(tiles, nextTiles)

        val playerOrder: MutableList<Int> = mutableListOf(1,1,2,2)
        playerOrder.shuffle(random)
        log.debug("Player Order: $playerOrder")

        log.debug("Assigning players automatically, with best tile priority.")
        for(i in 3 downTo 0) {
            currentTiles[i].player = playerOrder.removeAt(0)
        }

        log.debug("Current Tiles:")
        currentTiles.forEach { log.debug(it.toString()) }

    }

    private fun dealTiles(tiles: MutableList<Tile>, destination: MutableList<Tile>) {
        for (i in 0..3) {
            destination.add(tiles.removeAt(0))
        }
        log.debug("Tiles:")
        destination.forEach { log.debug(it.toString()) }
    }
}