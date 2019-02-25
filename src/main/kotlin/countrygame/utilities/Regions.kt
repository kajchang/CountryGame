package countrygame.utilities

val Regions: Map<String, Map<String, Any?>> = mapOf(
        "asia" to mapOf(
                "initialZoom" to 0.95,
                "initialPoint" to mapOf(
                        "longitude" to 85.69564437934545,
                        "latitude" to 24.353068430752273
                ),
                "include" to mutableListOf("TW", "BD", "TM", "KZ", "SY", "KH", "IN", "IO", "MY", "CN", "PS", "YE", "KW", "JO", "IL", "UZ", "LB", "BT", "AE", "GE", "MN", "KG", "SA", "LK", "AM", "JP", "SG", "NP", "OM", "MO", "AZ", "TH", "MV", "BH", "CC", "VN", "HK", "TJ", "KP", "PK", "TR", "ID", "LA", "QA", "PH", "MM", "CX", "AF", "KR", "IR", "IQ", "BN")
        ),
        "africa" to mapOf(
                "initialZoom" to 1.0,
                "initialPoint" to mapOf(
                        "longitude" to 16.2275,
                        "latitude" to -5.77602839982626
                ),
                "include" to mutableListOf("KE", "SD", "TN", "MG", "DZ", "CI", "CV", "TG", "BI", "NA", "GN", "SH", "ML", "GM", "ZM", "LR", "ZW", "MR", "CF", "ZA", "GW", "GA", "UG", "MU", "ST", "RE", "YT", "TZ", "SS", "MZ", "SC", "CG", "KM", "CD", "BJ", "AO", "LS", "BF", "SZ", "ER", "EG", "DJ", "BW", "SO", "MW", "TD", "GH", "MA", "NG", "NE", "RW", "SL", "ET", "SN", "CM", "GQ", "LY", "EH")
        ),
        "south-america" to mapOf(
                "initialZoom" to 0.95,
                "initialPoint" to mapOf(
                        "longitude" to -63.22610000000001,
                        "latitude" to -19.86896924235985
                ),
                "include" to mutableListOf("SR", "EC", "GF", "FK", "BO", "VE", "CL", "BR", "PE", "PY", "UY", "CO", "AR", "GY", "BZ", "TC", "MS", "GP", "HT", "MF", "NI")
        ),
        "north-america" to mapOf(
                "initialZoom" to 1.2,
                "initialPoint" to mapOf(
                        "longitude" to -108.06094713659054,
                        "latitude" to 54.20595084363595
                ),
                "include" to mutableListOf("PM", "PA", "GT", "SX", "MX", "CR", "AW", "BS", "JM", "VG", "AI", "GL", "CW", "BQ", "BL", "TT", "DO", "KN", "PR", "BB", "CA", "SV", "VC", "MQ", "HN", "BM", "DM", "KY", "US", "GD", "LC", "AG", "VI", "CU")
        ),
        "europe" to mapOf(
                "initialZoom" to 2.0,
                "initialPoint" to mapOf(
                        "longitude" to 38.086777890879475,
                        "latitude" to 60.79916329493254
                ),
                "include" to mutableListOf("XK", "GR", "PL", "MT", "SJ", "RU", "DK", "AT", "BA", "GI", "BE", "PT", "SI", "DE", "GB", "RS", "HU", "AD", "MK", "AX", "UA", "BG", "LV", "CH", "GG", "BY", "IT", "SE", "SK", "LT", "RO", "SM", "CZ", "ES", "AL", "VA", "IM", "ME", "NO", "MD", "EE", "FR", "FO", "FI", "MC", "JE", "HR", "IE", "LI", "LU", "IS", "NL", "CY")
        )
)
