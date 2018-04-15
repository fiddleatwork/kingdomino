package michael.kingdomino

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class Game {
    private val log = LoggerFactory.getLogger(Game::class.java)

    private val random = Random(System.currentTimeMillis())

    @Autowired
    private lateinit var tileRepository: TileRepository


    fun start(): GameResult {
        val players = listOf(
                Player(0, BestScoreStrategy()),
//                Player(0, FirstAvailableStrategy()),
                Player(1, FirstAvailableStrategy()))
        var tileBox = TileBox(tileRepository, 2)
//        tileBox = tileBox.dealTiles()

        val playerOrder: MutableList<Int> = mutableListOf(0, 0, 1, 1)
        playerOrder.shuffle(random)
        log.debug("Player Order: $playerOrder")

        log.debug("Assigning players automatically, with best tile priority.")
        for (i in 3 downTo 0) {
            tileBox.currentTiles[i].player = playerOrder.removeAt(0)
        }

        log.debug("Current Tiles:")
        tileBox.currentTiles.forEach { log.debug(it.toString()) }

        log.debug("Starting game loop..")
        while (true) {
            while (tileBox.hasCurrent()) {
                tileBox = tileBox.takeCurrent()
                val tile = tileBox.current()
                val player = players[tile.player]

                //log.debug("")
                //log.debug("Player $player.id board before play:")
                //log.debug(player.board.render())
                player.play(tile)
                log.debug("")
                log.debug("Player $player.id board after play:")
                log.debug(player.board.render())

                if (tileBox.hasNext()) {
                    log.debug("Player " + player.id + " is picking tile for next round..")
                    for (i in 3 downTo 0) {
                        if (tileBox.nextTiles[i].player == -1) {
                            tileBox.nextTiles[i].player = player.id
                            break
                        }
                    }
                }
                log.debug("Next Tiles:")
                tileBox.nextTiles.forEach { log.debug(it.toString()) }
            }
            log.debug("Finished round")
            if (tileBox.nextTiles.isEmpty()) {
                log.debug("No more tiles, finished.")
                break
            }
            tileBox = tileBox.dealTiles()

            log.debug("For next round:")
            log.debug("Current Tiles:")
            tileBox.currentTiles.forEach { log.debug(it.toString()) }
            log.debug("Next Tiles:")
            tileBox.nextTiles.forEach { log.debug(it.toString()) }
        }
        log.debug("Game loop finished.")
        log.info("Player 0 score = " + players[0].board.score())
        log.info("Player 1 score = " + players[1].board.score())
        return when {
            players[0].board.score() < players[1].board.score() ->
                GameResult(Outcome.Win, players[1], players[1].board.score() - players[0].board.score())
            players[0].board.score() > players[1].board.score() ->
                GameResult(Outcome.Win, players[0], players[0].board.score() - players[1].board.score())
            else -> GameResult(Outcome.Tie, players[0], players[0].board.score())
        }
    }

}