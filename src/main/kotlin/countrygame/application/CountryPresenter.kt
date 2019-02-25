package countrygame.application

import amcharts4.am4geodata_worldHigh
import countrygame.utilities.*

class CountryPresenter(override val view: CountryView) : Presenter<CountryView, String> {
    private lateinit var selectedRegion: String
    private var countries = mutableListOf<dynamic>()

    init {
        view.presenter = this
        setRegion("world")
    }


    fun setRegion(region: String) {
        selectedRegion = region

        val circles = js("[]")

        @Suppress("UNCHECKED_CAST")
        val include = Regions[region]?.get("include") as MutableList<String>?

        @Suppress("UNCHECKED_CAST")
        val initialGeoPoint = Regions[region]?.get("initialPoint") as Map<String, Double>
        val initialZoom = Regions[region]?.get("initialZoom") as Double

        clearCountries()
        am4geodata_worldHigh.features.forEach { country ->
            if (include?.contains(country.properties.id as String) != false) {
                addCountry(country)

                // calculate area and center

                var biggestArea: Double? = null

                val overallLatCoords = mutableListOf<Double>()
                val overallLongCoords = mutableListOf<Double>()

                for (idx in 0 until country.geometry.coordinates.length as Int) {
                    val polygon = if (country.geometry.type == "Polygon") country.geometry.coordinates[idx] else country.geometry.coordinates[idx][0]

                    val latCoords = mutableListOf<Double>()
                    val longCoords = mutableListOf<Double>()

                    polygon.forEach { point ->
                        latCoords.add(point[0] as Double)
                        longCoords.add(point[1] as Double)
                    }

                    val area = irregularArea(latCoords, longCoords)

                    if (area > biggestArea ?: 0.0) {
                        biggestArea = area
                    }

                    overallLatCoords.addAll(latCoords)
                    overallLongCoords.addAll(longCoords)
                }

                if (biggestArea.unsafeCast<Double>() < 0.1) {
                    val center = centroid(overallLatCoords, overallLongCoords)
                    circles.push(nativeObject(mapOf(
                            "latitude" to center[1],
                            "longitude" to center[0],
                            "name" to country.properties.name,
                            "id" to country.properties.id
                    )))
                }
            }
        }

        view.displayRegion(region, include, initialZoom, initialGeoPoint, circles)
    }

    private fun addCountry(country: dynamic) {
        countries.add(country)
    }

    private fun randomCountry(): dynamic {
        return countries.random()
    }

    private fun clearCountries() {
        countries.clear()
    }

    override fun dispose(): String {
        view.dispose()
        return selectedRegion
    }

    override fun restore(state: String) {
        setRegion(state)
    }
}
