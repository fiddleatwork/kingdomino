package michael.kingdomino

data class Square(val squareType: SquareType, val crowns: Int) {

    override fun toString(): String = "${squareType.name.toCharArray()[0]}:$crowns"

}
