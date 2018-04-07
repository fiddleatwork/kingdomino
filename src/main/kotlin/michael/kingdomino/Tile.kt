package michael.kingdomino

data class Tile(val id: Int, val squares: List<Square>, var player: Int = -1)