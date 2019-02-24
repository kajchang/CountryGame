package countrygame

import amcharts4.*
import org.w3c.dom.*
import org.w3c.dom.events.Event

class WebCountryView(private val buttonDiv: HTMLDivElement, private val mapDiv: HTMLDivElement, private val regionElement: Element) : CountryView {
    override lateinit var presenter: CountryPresenter

    private val setRegion: (Event) -> Unit = {event ->
        val button = event.target as HTMLButtonElement
        presenter.setRegion(button.name)
    }

    override fun displayRegion(regionName: String, include: MutableList<String>?) {
        regionElement.textContent = regionName

        // in the future, hardcode specific regions

        val map = create(mapDiv.id, MapChart)
        map.geodata = am4geodata_worldHigh
        map.projection = Miller()
        val series: dynamic = map.series.push(MapPolygonSeries())
        series.useGeodata = true
        if (include != null) {
            val result: dynamic = js("[]")
            for (value in include) {
                result.push(value)
            }
            series.include = result
        }
        val hs: dynamic = series.mapPolygons.template.states.create("hover")
        hs.properties.fill = color("#AECAA7")
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
