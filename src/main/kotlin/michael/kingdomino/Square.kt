package michael.kingdomino

data class Square(val type: SquareType, val crowns: Int, var x: Int = -1, var y: Int = -1) {
    override fun toString(): String = "${type.name.toCharArray()[0]}:$crowns"
}

