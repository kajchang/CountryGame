package countrygame

interface CountryView {
    var presenter: CountryPresenter
    fun displayRegion(regionName: String, include: MutableList<String>?)
    fun dispose()
}
