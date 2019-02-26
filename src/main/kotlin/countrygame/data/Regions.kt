package countrygame.data

import amcharts4.geodata.*
import countrygame.utilities.nativeObject

interface Region {
        val initialZoom: Double
        val initialPoint: Coordinate
        val include: MutableList<String>?
        val geodata: dynamic
}

class Coordinate(private val latitude: Double, private val longitude: Double) {
        fun toObject(): dynamic {
                return nativeObject(mapOf(
                        "latitude" to latitude,
                        "longitude" to longitude
                ))
        }
}

object Asia : Region {
        override val initialZoom: Double = 0.95
        override val initialPoint: Coordinate = Coordinate(24.353068430752273, 85.69564437934545)
        override val include: MutableList<String>? = mutableListOf("TW", "BD", "TM", "KZ", "SY", "KH", "IN", "IO", "MY", "CN", "PS", "YE", "KW", "JO", "IL", "UZ", "LB", "BT", "AE", "GE", "MN", "KG", "SA", "LK", "AM", "JP", "SG", "NP", "OM", "MO", "AZ", "TH", "MV", "BH", "CC", "VN", "HK", "TJ", "KP", "PK", "TR", "ID", "LA", "QA", "PH", "MM", "CX", "AF", "KR", "IR", "IQ", "BN")
        override val geodata: dynamic = am4geodata_worldHigh
}

object Africa : Region {
        override val initialZoom: Double = 1.0
        override val initialPoint: Coordinate = Coordinate(-5.77602839982626, 16.2275)
        override val include: MutableList<String>? = mutableListOf("KE", "SD", "TN", "MG", "DZ", "CI", "CV", "TG", "BI", "NA", "GN", "SH", "ML", "GM", "ZM", "LR", "ZW", "MR", "CF", "ZA", "GW", "GA", "UG", "MU", "ST", "RE", "YT", "TZ", "SS", "MZ", "SC", "CG", "KM", "CD", "BJ", "AO", "LS", "BF", "SZ", "ER", "EG", "DJ", "BW", "SO", "MW", "TD", "GH", "MA", "NG", "NE", "RW", "SL", "ET", "SN", "CM", "GQ", "LY", "EH")
        override val geodata: dynamic = am4geodata_worldHigh
}

object SouthAmerica: Region {
        override val initialZoom: Double = 0.95
        override val initialPoint: Coordinate = Coordinate(-19.86896924235985, -63.22610000000001)
        override val include: MutableList<String>? = mutableListOf("SR", "EC", "GF", "FK", "BO", "VE", "CL", "BR", "PE", "PY", "UY", "CO", "AR", "GY", "BZ", "TC", "MS", "GP", "HT", "MF", "NI")
        override val geodata: dynamic = am4geodata_worldHigh
}

object NorthAmerica: Region {
        override val initialZoom: Double = 1.2
        override val initialPoint: Coordinate = Coordinate(54.20595084363595, -108.06094713659054)
        override val include: MutableList<String>? = mutableListOf("PM", "PA", "GT", "SX", "MX", "CR", "AW", "BS", "JM", "VG", "AI", "GL", "CW", "BQ", "BL", "TT", "DO", "KN", "PR", "BB", "CA", "SV", "VC", "MQ", "HN", "BM", "DM", "KY", "US", "GD", "LC", "AG", "VI", "CU")
        override val geodata: dynamic = am4geodata_worldHigh
}

object Europe : Region {
        override val initialZoom: Double = 2.0
        override val initialPoint: Coordinate = Coordinate(60.79916329493254, 38.086777890879475)
        override val include: MutableList<String>? = mutableListOf("XK", "GR", "PL", "MT", "SJ", "RU", "DK", "AT", "BA", "GI", "BE", "PT", "SI", "DE", "GB", "RS", "HU", "AD", "MK", "AX", "UA", "BG", "LV", "CH", "GG", "BY", "IT", "SE", "SK", "LT", "RO", "SM", "CZ", "ES", "AL", "VA", "IM", "ME", "NO", "MD", "EE", "FR", "FO", "FI", "MC", "JE", "HR", "IE", "LI", "LU", "IS", "NL", "CY")
        override val geodata: dynamic = am4geodata_worldHigh
}

object UnitedStates: Region {
        override val initialZoom: Double = 1.0
        override val initialPoint: Coordinate = Coordinate(48.97460309044186, -122.59080000000002)
        override val include: MutableList<String>? = null
        override val geodata = am4geodata_usaHigh
}

val Regions: Map<String, Region> = mapOf(
        "asia" to Asia,
        "africa" to Africa,
        "south-america" to SouthAmerica,
        "north-america" to NorthAmerica,
        "europe" to Europe,
        "united-states" to UnitedStates
)
