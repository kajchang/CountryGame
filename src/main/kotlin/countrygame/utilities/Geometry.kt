package countrygame.utilities

// https://www.wikihow.com/Calculate-the-Area-of-a-Polygon
fun irregularArea(latCoords: List<Double>, longCoords: List<Double>): Double {
    require(latCoords.size == longCoords.size) {
        "latCoords is ${latCoords.size} long and longCoords is ${longCoords.size} long"
    }

    var acc1 = 0.0
    var acc2 = 0.0

    for (idx in 0 until longCoords.size - 1) {
        acc1 += (longCoords[idx] * latCoords[idx + 1])
    }

    for (idx in 0 until latCoords.size - 1) {
        acc2 += (latCoords[idx] * longCoords[idx + 1])
    }

    return (acc1 - acc2) / 2
}


// centroid
fun centroid(latCoords: List<Double>, longCoords: List<Double>): DoubleArray {
    require(latCoords.size == longCoords.size) {
        "latCoords is ${latCoords.size} long and longCoords is ${longCoords.size} long"
    }

    var acc1 = 0.0
    var acc2 = 0.0

    for (idx in 0 until latCoords.size) {
        acc1 += latCoords[idx]
        acc2 += longCoords[idx]
    }

    return doubleArrayOf(acc1 / latCoords.size, acc2 / longCoords.size)
}
