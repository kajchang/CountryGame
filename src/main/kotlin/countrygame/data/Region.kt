package countrygame.data

interface Region {
    val initialZoom: Double
    val initialPoint: Coordinate
    val include: MutableList<String>?
    val geodata: dynamic
}
