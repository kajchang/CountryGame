package countrygame

class CountryPresenter(override val view: CountryView) : Presenter<CountryView, String> {
    init {
        view.presenter = this
    }

    private var displayedCountry: String = ""

    fun setCountry(country: String) {
        displayedCountry = country
        // view.displayCountry(country)
    }

    override fun dispose(): String {
        view.dispose()
        return displayedCountry
    }

    override fun restore(state: String) {
        displayedCountry = state
        // view.displayCountry(displayedCountry)
    }
}
