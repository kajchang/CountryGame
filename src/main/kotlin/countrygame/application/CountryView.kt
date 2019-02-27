package countrygame.application

import countrygame.data.Coordinate

interface CountryView {
    var presenter: CountryPresenter
    fun setOptions(options: Set<String>)
    fun displayRegion(regionName: String, geodata: dynamic, include: MutableList<String>?, initialZoom: Double, initialPoint: Coordinate, circles: dynamic)
    fun displayCountryToFind(country: String)
    fun updateTimer(timer: Int)
    fun displayWin()
    fun dispose()
}
