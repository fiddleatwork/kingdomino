package michael.kingdomino

data class Square(val type: SquareType, val crowns: Int, val coordinates: Coordinates = Coordinates(-1,-1)) {

    override fun toString(): String = "${type.name.toCharArray()[0]}:$crowns"

//    fun withCoordinates(coordinates: Coordinates) : Square {
//        return copy(coordinates = coordinates)
//    }

}

