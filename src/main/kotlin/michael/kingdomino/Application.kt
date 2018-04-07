package michael.kingdomino

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
    private val log = LoggerFactory.getLogger(Application::class.java)

    @Autowired
    private lateinit var game: Game

    @Bean
    fun init() = CommandLineRunner {
        log.info("Start")
        val n = 0
        for(i in 0..n) {
            game.start()
        }
        log.info("Finished")
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

