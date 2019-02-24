package countrygame

class CountryPresenter(override val view: CountryView) : Presenter<CountryView, String> {
    private lateinit var selectedRegion: String
    private var countries = mutableListOf<String>()

    init {
        view.presenter = this
        setRegion("world")
    }


    fun setRegion(region: String) {
        selectedRegion = region
        view.displayRegion(region.replace('-', ' '), Regions[region])
    }

    fun addCountry(country: String) {
        countries.add(country)
    }

    fun randomCountry(): String {
        return countries.random()
    }

    fun clearCountries() {
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
