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

    private var player1: Player = Player(1)

    private var player2: Player = Player(2)

    fun start() {
        val allTiles = tileRepository.findAll()
        Collections.shuffle(allTiles, Random(System.currentTimeMillis()))
        val tiles = allTiles.subList(0, allTiles.size/2)

        log.debug("Tiles:")
        tiles.forEach { log.debug(it.toString()) }
    }
}