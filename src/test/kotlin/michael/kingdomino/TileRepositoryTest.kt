package michael.kingdomino

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class TileRepositoryTest {

    @Autowired
    lateinit var tileRepository : TileRepository

    @Test
    fun `should load all tiles`() {
        val tiles = tileRepository.findAll()
    }

}
