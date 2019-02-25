package countrygame.application

import amcharts4.am4geodata_worldHigh
import countrygame.utilities.*

class CountryPresenter(override val view: CountryView) : Presenter<CountryView, Map<String, Any?>> {
    private lateinit var selectedRegion: String
    private var gameStarted = false
    private var countryToFind: String = ""
    private var countries = mutableListOf<String>()

    init {
        view.presenter = this
        setRegion("world")
    }

    fun startGame() {
        if (!gameStarted) {
            gameStarted = true
            countryToFind = popCountry()
            view.displayCountryToFind(countryToFind)
        }
    }


    fun setRegion(region: String) {
        selectedRegion = region

        val circles = js("[]")

        @Suppress("UNCHECKED_CAST")
        val include = Regions[region]?.get("include") as MutableList<String>?

        @Suppress("UNCHECKED_CAST")
        val initialGeoPoint = Regions[region]?.get("initialPoint") as Map<String, Double>
        val initialZoom = Regions[region]?.get("initialZoom") as Double

        reset()
        am4geodata_worldHigh.features.forEach { country ->
            if (include?.contains(country.properties.id as String) != false) {
                addCountry(country.properties.name as String)

                // calculate area and center for small countries

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

                if (biggestArea.unsafeCast<Double>() < 0.05) {
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

    private fun addCountry(country: String) {
        countries.add(country)
    }

    private fun popCountry(): String {
        return countries.removeAt((0 until countries.size).random())
    }

    private fun reset() {
        countries.clear()
        gameStarted = false
    }

    override fun dispose(): Map<String, Any?> {
        view.dispose()
        return mapOf(
                "selectedRegion" to selectedRegion,
                "gameStarted" to gameStarted,
                "countryToFind" to countryToFind
        )
    }

    override fun restore(state: Map<String, Any?>) {
        state["selectedRegion"]?.let {selectedRegion ->
            setRegion(selectedRegion as String)
        }
        state["gameStarted"]?.let {gameStarted ->
            if (gameStarted as Boolean) {
                countryToFind = state["countryToFind"] as String
                countries.remove(countryToFind)
                view.displayCountryToFind(countryToFind)
            }
        }
    }
}