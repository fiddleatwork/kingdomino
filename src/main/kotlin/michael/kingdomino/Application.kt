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
        val n = 10000
        var player1Wins = 0
        var player2Wins = 0
        var ties = 0
        for(i in 0..n) {
            log.info("Game # $i")
            val result = game.start()
            when {
                result.outcome == Outcome.Tie -> ties++
                result.winner.id == 0 -> player1Wins++
                result.winner.id == 1 -> player2Wins++
            }
        }
        log.info("Player 1 wins: $player1Wins = " + player1Wins.toDouble()/n)
        log.info("Player 2 wins: $player2Wins = " + player2Wins.toDouble()/n)
        log.info("Ties: $ties")

        log.info("Finished")
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

