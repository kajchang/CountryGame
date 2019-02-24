package countrygame

class CountryPresenter(override val view: CountryView) : Presenter<CountryView, String> {
    init {
        view.presenter = this
    }

    private var selectedRegion: String = ""

    fun setRegion(region: String) {
        selectedRegion = region
        view.displayRegion(selectedRegion)
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
