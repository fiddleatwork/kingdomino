package michael.kingdomino

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.error.ShouldContainOnlyDigits
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class BoardTest {

    private val log = LoggerFactory.getLogger(BoardTest::class.java)

    @Test
    fun `should render board`() {
        val render = Board().render()
        log.debug("Rendered board:")
        log.debug(render)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should not place tile in invalid location`() {
        val board = Board()
        board.place(
                Tile(0,
                listOf(Square(SquareType.Field, 0), Square(SquareType.Field, 1))),
                Direction.LeftToRight,
                0,
                0)

    }

    @Test
    fun `should place tile`() {
        val board = Board()
        log.info(board.render())
        val newBoard = board.place(
                Tile(0,
                        listOf(Square(SquareType.Field, 0), Square(SquareType.Field, 1))),
                Direction.LeftToRight,
                4,
                5)
        log.info("old board" + board.render())
        assertThat(board.squareAt(4,5)).isEqualTo(Square(SquareType.Empty, 0))
        assertThat(board.squareAt(5,5)).isEqualTo(Square(SquareType.Empty, 0))
        log.info("new board" + newBoard.render())
        assertThat(newBoard.squareAt(4,5)).isEqualTo(Square(SquareType.Field, 0, Coordinates(4,5)))
        assertThat(newBoard.squareAt(5,5)).isEqualTo(Square(SquareType.Field, 1, Coordinates(5,5)))
    }


    /**
     * The board setup is not valid, this was used for debugging purposes and left as a template for future tests.
     */
    // TODO Rewrite test using board.play API
    @Test
    fun `should calculate score`() {

//        [   ][   ][   ][   ][   ][   ][   ][   ][   ]
//        [   ][   ][   ][   ][   ][   ][   ][   ][   ]
//        [   ][   ][   ][   ][F:0][F:0][S:1][S:1][F:0]
//        [   ][   ][   ][   ][S:1][F:0][S:1][S:1][F:0]
//        [   ][   ][   ][   ][C:0][F:0][S:1][F:0][S:1]
//        [   ][   ][   ][   ][F:0][S:1][S:1][F:0][S:1]
//        [   ][   ][   ][   ][F:0][S:1][F:0][F:0][S:1]
//        [   ][   ][   ][   ][   ][   ][   ][   ][   ]
//        [   ][   ][   ][   ][   ][   ][   ][   ][   ]

        val map = Array(9) { Array(9) { Square(SquareType.Empty, 0) } }
        map[4][6] = Square(SquareType.Field, 0)
        map[5][6] = Square(SquareType.Field, 0)
        map[6][6] = Square(SquareType.Swamp, 1)
        map[7][6] = Square(SquareType.Swamp, 1)
        map[8][6] = Square(SquareType.Field, 0)

        map[4][5] = Square(SquareType.Swamp, 1)
        map[5][5] = Square(SquareType.Field, 0)
        map[6][5] = Square(SquareType.Swamp, 1)
        map[7][5] = Square(SquareType.Swamp, 1)
        map[8][5] = Square(SquareType.Field, 0)

        map[4][4] = Square(SquareType.Center, 0)
        map[5][4] = Square(SquareType.Field, 0)
        map[6][4] = Square(SquareType.Swamp, 1)
        map[7][4] = Square(SquareType.Field, 0)
        map[8][4] = Square(SquareType.Swamp, 1)

        map[4][3] = Square(SquareType.Field, 0)
        map[5][3] = Square(SquareType.Swamp, 1)
        map[6][3] = Square(SquareType.Swamp, 1)
        map[7][3] = Square(SquareType.Field, 0)
        map[8][3] = Square(SquareType.Swamp, 1)

        map[4][2] = Square(SquareType.Field, 0)
        map[5][2] = Square(SquareType.Swamp, 1)
        map[6][2] = Square(SquareType.Field, 0)
        map[7][2] = Square(SquareType.Field, 0)
        map[8][2] = Square(SquareType.Swamp, 1)

        for(x in 0..8) {
            for(y in 0..8) {
                map[x][y] = map[x][y].copy(coordinates = Coordinates(x,y))
            }
        }

        val board = Board(BoardMap(map))
        assertThat(board.score()).isEqualTo(68)



    }

}
