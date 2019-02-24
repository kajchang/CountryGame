package countrygame.application

import amcharts4.am4geodata_worldHigh
import countrygame.utilities.Regions

class CountryPresenter(override val view: CountryView) : Presenter<CountryView, String> {
    private lateinit var selectedRegion: String
    private var countries: MutableList<dynamic> = mutableListOf()

    init {
        view.presenter = this
        setRegion("world")
    }


    fun setRegion(region: String) {
        selectedRegion = region

        @Suppress("UNCHECKED_CAST")
        val include = Regions[region]?.get("include") as MutableList<String>?

        @Suppress("UNCHECKED_CAST")
        val initialGeoPoint = Regions[region]?.get("initialPoint") as Map<String, Double>
        val initialZoom = Regions[region]?.get("initialZoom") as Double

        clearCountries()
        am4geodata_worldHigh.features.forEach {country ->
            if (include?.contains(country.properties.id as String) != false) {
                addCountry(country)
            }
        }

        view.displayRegion(region, include, initialZoom, initialGeoPoint)
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
