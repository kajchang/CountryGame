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
        override val include: MutableList<String>? = mutableListOf("TW", "BD", "TM", "KZ", "SY", "KH", "IN", "MY", "CN", "PS", "YE", "KW", "JO", "IL", "UZ", "LB", "BT", "AE", "GE", "MN", "KG", "SA", "LK", "AM", "JP", "SG", "NP", "OM", "MO", "AZ", "TH", "BH", "VN", "HK", "TJ", "KP", "PK", "TR", "ID", "LA", "QA", "PH", "MM", "AF", "KR", "IR", "IQ", "BN")
        override val geodata: dynamic = am4geodata_worldHigh
}

object Africa : Region {
        override val initialZoom: Double = 1.0
        override val initialPoint: Coordinate = Coordinate(-5.77602839982626, 16.2275)
        override val include: MutableList<String>? = mutableListOf("KE", "SD", "TN", "MG", "DZ", "CI", "TG", "BI", "NA", "GN", "ML", "GM", "ZM", "LR", "ZW", "MR", "CF", "ZA", "GW", "GA", "UG", "TZ", "SS", "MZ", "CG", "CD", "BJ", "AO", "LS", "BF", "SZ", "ER", "EG", "DJ", "BW", "SO", "MW", "TD", "GH", "MA", "NG", "NE", "RW", "SL", "ET", "SN", "CM", "GQ", "LY", "EH")
        override val geodata: dynamic = am4geodata_worldHigh
}

object SouthAmerica: Region {
        override val initialZoom: Double = 0.95
        override val initialPoint: Coordinate = Coordinate(-19.86896924235985, -63.22610000000001)
        override val include: MutableList<String>? = mutableListOf("SR", "EC", "GF", "BO", "VE", "CL", "BR", "PE", "PY", "UY", "CO", "AR", "GY")
        override val geodata: dynamic = am4geodata_worldHigh
}

object CentralAmerica: Region {
        override val initialZoom: Double = 1.0
        override val initialPoint: Coordinate = Coordinate(15.789250770393188, -88.44455939810537)
        override val include: MutableList<String>? = mutableListOf("BZ", "NI", "HT", "PA", "GT", "MX", "CR", "BS", "JM", "TT", "DO", "PR", "BB", "SV", "MQ", "HN", "DM", "GD", "AG", "CU")
        override val geodata: dynamic = am4geodata_worldHigh
}

object Europe : Region {
        override val initialZoom: Double = 2.0
        override val initialPoint: Coordinate = Coordinate(60.79916329493254, 38.086777890879475)
        override val include: MutableList<String>? = mutableListOf("XK", "GR", "PL", "MT", "RU", "DK", "AT", "BA", "GI", "BE", "PT", "SI", "DE", "GB", "RS", "HU", "AD", "MK", "UA", "BG", "LV", "CH", "BY", "IT", "SE", "SK", "LT", "RO", "SM", "CZ", "ES", "AL", "VA", "ME", "NO", "MD", "EE", "FR", "FI", "MC", "HR", "IE", "LI", "LU", "IS", "NL", "CY")
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
        "central-america" to CentralAmerica,
        "europe" to Europe,
        "united-states" to UnitedStates
)
