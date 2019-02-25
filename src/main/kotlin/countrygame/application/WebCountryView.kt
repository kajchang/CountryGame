package countrygame.application

import amcharts4.*
import countrygame.utilities.nativeArray
import countrygame.utilities.nativeObject
import org.w3c.dom.*
import org.w3c.dom.events.Event

class WebCountryView(
        private val buttonDiv: HTMLDivElement,
        private val startButton: HTMLButtonElement,
        private val countryToFindSpan: Element,
        private val mapDiv: HTMLDivElement,
        private val regionElement: Element) : CountryView {
    override lateinit var presenter: CountryPresenter

    private val setRegion: (Event) -> Unit = { event ->
        val button = event.target as HTMLButtonElement
        presenter.setRegion(button.name)
    }

    private val startGame: (Event) -> Unit = {_ ->
        presenter.startGame()
    }

    private val checkCountry: (dynamic) -> Unit = { event ->
        console.log(event.target.dataItem.dataContext.name as String)
    }

    override fun displayRegion(regionName: String, include: MutableList<String>?, initialZoom: Double, initialPoint: Map<String, Double>, circles: dynamic) {
        regionElement.textContent = regionName.replace('-', ' ')
        countryToFindSpan.textContent = ""

        val map = create(mapDiv.id, MapChart)
        map.geodata = am4geodata_worldHigh
        map.projection = Miller()
        map.homeZoomLevel = initialZoom
        map.seriesContainer.draggable = false
        map.seriesContainer.resizable = false
        map.seriesContainer.events.disableType("doublehit")
        map.chartContainer.background.events.disableType("doublehit")
        map.maxZoomLevel = initialZoom
        map.minZoomLevel = initialZoom
        map.homeGeoPoint = nativeObject(initialPoint)

        val mapPolygonSeries = map.series.push(MapPolygonSeries())
        val mapPolygon = mapPolygonSeries.mapPolygons.template
        mapPolygonSeries.useGeodata = true
        if (include != null) {
            mapPolygonSeries.include = nativeArray(include)
        }
        mapPolygon.events.on("hit", checkCountry)

        val mapHover = mapPolygon.states.create("hover")
        mapHover.properties.fill = color("#AECAA7")

        val clickableSeries = map.series.push(MapImageSeries())
        val clickable = clickableSeries.mapImages.template
        clickable.nonScaling = true
        clickable.propertyFields.latitude = "latitude"
        clickable.propertyFields.longitude = "longitude"
        clickableSeries.data = circles
        clickable.events.on("hit", checkCountry)

        val circle = clickable.createChild(Circle)
        circle.radius = 5
        circle.fill = color("#D9D9D9")
        circle.stroke = color("#000000")
        circle.strokeWidth = 0.25

        val circleHover = circle.states.create("hover")
        circleHover.properties.fill = color("#AECAA7")
    }

    override fun displayCountryToFind(country: String) {
        countryToFindSpan.textContent = "Find This Country: $country"
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

        startButton.addEventListener("click", startGame)
    }

    private fun unregister() {
        val buttons = buttonDiv.getElementsByTagName("button")

        for (idx in 0 until buttons.length) {
            val button = buttons[idx] as HTMLButtonElement
            button.removeEventListener("click", setRegion)
        }

        startButton.removeEventListener("click", startGame)
    }
}
