package countrygame

class CountryPresenter(override val view: CountryView) : Presenter<CountryView, String> {
    private var selectedRegion = "world"
    private var countries = mutableListOf<String>()

    init {
        view.presenter = this
        view.displayRegion(selectedRegion)
    }


    fun setRegion(region: String) {
        selectedRegion = region
        view.displayRegion(selectedRegion)
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
        selectedRegion = state
        view.displayRegion(selectedRegion)
    }
}
