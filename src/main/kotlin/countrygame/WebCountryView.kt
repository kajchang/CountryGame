package countrygame

import amcharts4.*
import org.w3c.dom.*
import org.w3c.dom.events.Event

class WebCountryView(private val buttonDiv: HTMLDivElement, private val mapDiv: HTMLDivElement) : CountryView {
    override lateinit var presenter: CountryPresenter

    private val setRegion: (Event) -> Unit = {event ->
        val button = event.target as HTMLButtonElement
        presenter.setRegion(button.name)
    }

    override fun displayRegion(region: String) {
        // in the future, hardcode specific regions

        val map = create(mapDiv.id, MapChart)
        map.seriesContainer.draggable = false
        map.seriesContainer.resizable = false
        map.maxZoomLevel = 1
        map.geodata = am4geodata_worldHigh
        map.projection = Miller()
        val series: dynamic = map.series.push(MapPolygonSeries())
        series.useGeodata = true
        val hs: dynamic = series.mapPolygons.template.states.create("hover")
        hs.properties.fill = color("#AECAA7")
        presenter.clearCountries()
        am4geodata_worldHigh.features.forEach { country ->
            presenter.addCountry(country.properties.name as String)
        }
    }

    init {
        register()
    }

    override fun dispose() {
        unregister()
    }

    private fun register() {
        val buttons = buttonDiv.getElementsByTagName("button")

        for (idx in 0 until buttons.length) {
            val button = buttons[idx] as HTMLButtonElement
            button.addEventListener("click", setRegion)
        }
    }

    private fun unregister() {
        val buttons = buttonDiv.getElementsByTagName("button")

        for (idx in 0 until buttons.length) {
            val button = buttons[idx] as HTMLButtonElement
            button.removeEventListener("click", setRegion)
        }
    }
}
