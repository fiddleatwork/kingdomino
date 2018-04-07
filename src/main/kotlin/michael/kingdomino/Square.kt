package michael.kingdomino

data class Square(val type: SquareType, val crowns: Int) {

    override fun toString(): String = "${type.name.toCharArray()[0]}:$crowns"

}
