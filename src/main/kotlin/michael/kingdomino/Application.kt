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
        log.info("")
        game.start()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

