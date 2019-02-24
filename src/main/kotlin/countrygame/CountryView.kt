package countrygame

interface CountryView {
    var presenter: CountryPresenter
    fun displayRegion(regionName: String, include: MutableList<String>?, initialZoom: Double, initialPoint: Map<String, Double>)
    fun dispose()
}
