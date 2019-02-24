package countrygame

interface CountryView {
    var presenter: CountryPresenter
    fun displayRegion(region: String)
    fun dispose()
}
