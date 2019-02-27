package countrygame.data

import countrygame.utilities.nativeObject

class Coordinate(private val latitude: Double, private val longitude: Double) {
    fun toObject(): dynamic {
        return nativeObject(mapOf(
                "latitude" to latitude,
                "longitude" to longitude
        ))
    }
}
