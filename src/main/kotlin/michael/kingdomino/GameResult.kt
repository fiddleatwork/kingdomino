package michael.kingdomino

enum class Outcome {
    Win, Tie
}

data class GameResult(val outcome: Outcome, val winner: Player, val scoreDelta:Int)