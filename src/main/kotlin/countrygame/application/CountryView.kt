package countrygame.application

interface CountryView {
    var presenter: CountryPresenter
    fun displayRegion(regionName: String, geodata: dynamic, include: MutableList<String>?, initialZoom: Double, initialPoint: Map<String, Double>, circles: dynamic)
    fun displayCountryToFind(country: String)
    fun updateTimer(timer: Int)
    fun displayWin()
    fun dispose()
}
