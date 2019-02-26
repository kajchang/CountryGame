package countrygame.application

import amcharts4.geodata.am4geodata_worldHigh
import countrygame.utilities.*
import kotlin.browser.window

class CountryPresenter(override val view: CountryView) : Presenter<CountryView, Map<String, Any?>> {
    private lateinit var selectedRegion: String
    private var gameStarted = false
    private var firstStart = true

    private var countryToFind: String = ""
    private var countries = mutableListOf<String>()
    private var finishedCountries = mutableListOf<String>()

    private var timer = 0
    private var interval: Int? = null

    init {
        view.presenter = this
        setRegion("asia")
    }

    fun startGame() {
        if (!gameStarted) {
            gameStarted = true
            if (!firstStart) {
                setRegion(selectedRegion)
            }
            firstStart = false
            nextCountry()
            view.updateTimer(timer)

            interval = window.setInterval({
                timer++
                view.updateTimer(timer)
            }, 1000)
        }
    }

    fun checkCountry(country: String): Boolean {
        if (country == countryToFind) {
            finishedCountries.add(country)
            if (countries.size == 0) {
                window.clearInterval(interval as Int)
                interval = null
                gameStarted = false
                view.displayWin()
            } else {
                nextCountry()
            }
            return true
        }

        return false
    }

    fun setRegion(region: String) {
        selectedRegion = region

        val circles = js("[]")
        val initialZoom: Double
        val initialGeoPoint: Map<String, Double>

        reset()

        if (Regions[region] != null) {
            @Suppress("UNCHECKED_CAST")
            val include = Regions[region]?.get("include") as MutableList<String>?

            @Suppress("UNCHECKED_CAST")
            initialGeoPoint = Regions[region]?.get("initialPoint") as Map<String, Double>
            initialZoom = Regions[region]?.get("initialZoom") as Double

            am4geodata_worldHigh.features.forEach { country ->
                if (include?.contains(country.properties.id as String) != false) {
                    addCountry(country.properties.name)

                    val countryStats = countryGeo(country)

                    val biggestArea = countryStats[0] as Int
                    @Suppress("UNCHECKED_CAST")
                    val overallLatCoords = countryStats[1] as List<Double>
                    @Suppress("UNCHECKED_CAST")
                    val overallLongCoords = countryStats[2] as List<Double>

                    if (biggestArea < 1) {
                        val center = centroid(overallLatCoords, overallLongCoords)
                        circles.push(nativeObject(mapOf(
                                "latitude" to center[1],
                                "longitude" to center[0],
                                "name" to country.properties.name
                        )))
                    }
                }
            }

            view.displayRegion(region, include, initialZoom, initialGeoPoint, circles)
        }
    }

    private fun countryGeo(country: dynamic): List<Any?> {
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

        return listOf(
                biggestArea,
                overallLatCoords,
                overallLongCoords
        )
    }

    private fun nextCountry() {
        countryToFind = popCountry()
        view.displayCountryToFind(countryToFind)
    }

    private fun reset() {
        countries.clear()
        gameStarted = false
        countryToFind = ""
        timer = 0
        if (interval != null) {
            window.clearInterval(interval as Int)
            interval = null
        }
    }

    private fun addCountry(country: String) {
        countries.add(country)
    }

    private fun popCountry(): String {
        return countries.removeAt((0 until countries.size).random())
    }

    override fun dispose(): Map<String, Any?> {
        if (interval != null) {
            window.clearInterval(interval as Int)
        }

        view.dispose()
        return mapOf(
                "selectedRegion" to selectedRegion,
                "gameStarted" to gameStarted
        )
    }

    override fun restore(state: Map<String, Any?>) {
        state["selectedRegion"]?.let {selectedRegion ->
            setRegion(selectedRegion as String)
        }
        state["gameStarted"]?.let {gameStarted ->
            if (gameStarted as Boolean) {
                startGame()
            }
        }
    }
}
