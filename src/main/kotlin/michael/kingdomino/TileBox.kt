package michael.kingdomino

import org.slf4j.LoggerFactory
import java.util.*

data class TileBox(private val tiles: List<Tile>, private val currentTileIndex: Int, internal val currentTiles: List<Tile>, internal val nextTiles: List<Tile>) {

    constructor(tiles: List<Tile>) : this(tiles.subList(8, tiles.size), -1, tiles.subList(0, 4), tiles.subList(4, 8))
//    constructor(tiles: List<Tile>) : this(tiles, 0, listOf(), listOf())

    constructor(tileRepository: TileRepository, numberPlayers: Int) :
            this(tileRepository.findAll().shuffled(Random(System.currentTimeMillis())).subList(0, 48 - 48 / numberPlayers))

    private val log = LoggerFactory.getLogger(TileBox::class.java)

    internal fun hasMoreTiles(): Boolean {
        return tiles.isNotEmpty()
    }

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
//        if (!hasMoreTiles()) {
//            log.error("No more tiles")
//            throw IllegalStateException("Can not deal tiles from an empty box!")
//        }

        return when {
            tiles.size > 4 -> TileBox(tiles.subList(4, tiles.size), 0, nextTiles, tiles.subList(0, 4))
            tiles.isNotEmpty() -> TileBox(listOf(), 0, nextTiles, tiles.subList(0,4))
            else -> TileBox(listOf(), 0, nextTiles, listOf())
        }

//        for (i in 0..3) {
//            currentTiles.add(tiles.removeAt(0))
//        }
//        log.debug("Tiles:")
//        destination.forEach { log.debug(it.toString()) }

//    log.debug("TileBox constructed with tiles:")
//    tiles.forEach { log.debug(it.toString()) }
    }


}
