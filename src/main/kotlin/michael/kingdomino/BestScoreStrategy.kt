package michael.kingdomino

import org.slf4j.LoggerFactory

class BestScoreStrategy : Strategy {

    private val log = LoggerFactory.getLogger(BestScoreStrategy::class.java)

    override fun play(board: Board, tile: Tile): Board {
        var bestBoard: Board = board
        var playable: Boolean = false
        for (y in 8 downTo 0) {
            for (x in 0..8) {
                Direction.values().forEach { d ->
                    if (board.accept(tile, d, x, y)) {
                        playable = true
                        val newBoard = board.copy()
                        newBoard.place(tile, d, x, y)
                        val newScore = newBoard.score()
                        val bestScore = bestBoard.score()
                        log.warn(" newScore $newScore bestScore $bestScore")
                        if (newScore >= bestScore) {
                            bestBoard = newBoard
                        } else {
                            log.warn("Score for position ($x,$y) is lower, so not using it")
                        }
                    }
                }
            }
        }
        if (!playable) {
            log.debug("Could not place the tile, discarding.")
        }
        return bestBoard
    }

    override fun toString(): String = this.javaClass.simpleName

}