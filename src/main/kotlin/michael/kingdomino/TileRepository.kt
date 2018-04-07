package michael.kingdomino

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

@Component
class TileRepository {

    private val log = LoggerFactory.getLogger(TileRepository::class.java)

    @Value("classpath:tiles.csv")
    private lateinit var tilesCsv: Resource


    fun findAll(): List<Tile> {
        val lines = Files.readAllLines(Paths.get(tilesCsv.uri), StandardCharsets.UTF_8)
        log.debug("lines = $lines")
        val tiles: MutableList<Tile> = mutableListOf()
        lines.forEach {
            if (it.isNotEmpty()) {
                val tokens = it.split(",")
                tiles.add(Tile(
                        Integer.parseInt(tokens[0]),
                        listOf(
                                Square(SquareType.valueOf(tokens[1]), Integer.parseInt(tokens[2])),
                                Square(SquareType.valueOf(tokens[3]), Integer.parseInt(tokens[4]))
                        ),
                        0))
            }
        }
        return tiles
    }

}