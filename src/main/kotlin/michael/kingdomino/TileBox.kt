package michael.kingdomino

import org.slf4j.LoggerFactory
import java.util.*

data class TileBox(private val tiles: List<Tile>, private val currentTileIndex: Int, private val currentTiles: List<Tile>, private val nextTiles: List<Tile>) {

    constructor(tiles: List<Tile>) : this(tiles.subList(8, tiles.size), -1, tiles.subList(0, 4), tiles.subList(4, 8))

    constructor(tileRepository: TileRepository, numberPlayers: Int) :
            this(tileRepository.findAll().shuffled(Random(System.currentTimeMillis())).subList(0, 48 - 48 / numberPlayers))

    private val log = LoggerFactory.getLogger(TileBox::class.java)

    fun current(): Tile {
        return currentTiles[currentTileIndex]
    }

    fun takeCurrent() : TileBox {
        return TileBox(tiles, currentTileIndex + 1, currentTiles, nextTiles)
    }

    fun hasCurrent() : Boolean {
        return currentTileIndex < 3
    }

    fun hasNext() : Boolean {
        return nextTiles.isNotEmpty()
    }

    fun dealTiles(): TileBox {
        return when {
            tiles.size > 4 -> TileBox(tiles.subList(4, tiles.size), 0, nextTiles, tiles.subList(0, 4))
            tiles.isNotEmpty() -> TileBox(listOf(), 0, nextTiles, tiles.subList(0,4))
            else -> TileBox(listOf(), 0, nextTiles, listOf())
        }
    }

    fun logCurrentTiles() {
        log.debug("Current Tiles:")
        currentTiles.forEach { log.debug(it.toString()) }
    }
    fun logNextTiles() {
        log.debug("Next Tiles:")
        nextTiles.forEach { log.debug(it.toString()) }
    }

    fun assignCurrentTile(i: Int, playerId: Int): TileBox {
        currentTiles[i].player = playerId
        return this
    }

    fun assignNextTile(i: Int, playerId: Int): TileBox {
        nextTiles[i].player = playerId
        return this
    }

    fun isNextTileUnclaimed(i: Int): Boolean {
        return nextTiles[i].player == -1
    }
}
