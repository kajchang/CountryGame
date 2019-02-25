package countrygame.application

import amcharts4.*
import countrygame.utilities.nativeArray
import countrygame.utilities.nativeObject
import jquery.jq
import org.w3c.dom.*
import org.w3c.dom.events.Event

class WebCountryView(private val buttonDiv: HTMLDivElement, private val mapDiv: HTMLDivElement, private val regionElement: Element) : CountryView {
    override lateinit var presenter: CountryPresenter

    private val setRegion: (Event) -> Unit = {event ->
        val button = event.target as HTMLButtonElement
        presenter.setRegion(button.name)
    }

    override fun displayRegion(regionName: String, include: MutableList<String>?, initialZoom: Double, initialPoint: Map<String, Double>, circles: dynamic) {
        regionElement.textContent = regionName.replace('-', ' ')

        val map = create(mapDiv.id, MapChart)
        map.geodata = am4geodata_worldHigh
        map.projection = Miller()
        map.maxPanOut = 0.1
        map.homeZoomLevel = initialZoom
        map.minZoomLevel = initialZoom
        map.homeGeoPoint = nativeObject(initialPoint)

        val mapSeries = map.series.push(MapPolygonSeries())
        mapSeries.useGeodata = true
        if (include != null) {
            mapSeries.include = nativeArray(include)
        }

        val mapHover = mapSeries.mapPolygons.template.states.create("hover")
        mapHover.properties.fill = color("#AECAA7")

        val clickableSeries = map.series.push(MapImageSeries())
        val clickable = clickableSeries.mapImages.template
        clickable.nonScaling = true
        clickable.propertyFields.latitude = "latitude"
        clickable.propertyFields.longitude = "longitude"
        clickableSeries.data = circles

        val circle = clickable.createChild(Circle)
        circle.radius = 5
        circle.fill = color("#D9D9D9")
        circle.stroke = color("#000000")
        circle.strokeWidth = 0.5

        val clickableHover = circle.states.create("hover")
        clickableHover.properties.fill = color("#AECAA7")
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
