package michael.kingdomino

enum class Direction(val secondSquareOffsetX: Int, val secondSquareOffsetY: Int) {
    LeftToRight(1,0),
    TopToBottom(0, -1),
    RightToLeft(-1, 0),
    BottomToTop(0, 1)
}