package countrygame

import amcharts4.am4geodata_worldHigh

class CountryPresenter(override val view: CountryView) : Presenter<CountryView, String> {
    private lateinit var selectedRegion: String
    private var countries: MutableList<dynamic> = mutableListOf()

    init {
        view.presenter = this
        setRegion("world")
    }


    fun setRegion(region: String) {
        selectedRegion = region

        clearCountries()
        am4geodata_worldHigh.features.forEach {country ->
            if (Regions[region]?.contains(country.properties.id as String) != false) {
                addCountry(country)
            }
        }

        view.displayRegion(region.replace('-', ' '), Regions[region])
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
