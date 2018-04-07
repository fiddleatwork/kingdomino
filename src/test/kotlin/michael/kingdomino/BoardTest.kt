package michael.kingdomino

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

}
