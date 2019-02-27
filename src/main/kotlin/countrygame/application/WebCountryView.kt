package countrygame.application

import amcharts4.core.*
import amcharts4.maps.*
import amcharts4.projections.Miller
import countrygame.data.Coordinate
import countrygame.utilities.*
import org.w3c.dom.*
import org.w3c.dom.events.Event
import kotlin.browser.document

class WebCountryView(
        private val buttonDiv: HTMLDivElement,
        private val startButton: HTMLButtonElement,
        private val countryToFindSpan: Element,
        private val mapDiv: HTMLDivElement,
        private val regionElement: Element,
        private val timerElement: Element) : CountryView {
    override lateinit var presenter: CountryPresenter

    private val setRegion: (Event) -> Unit = { event ->
        val button = event.target as HTMLButtonElement
        presenter.setRegion(button.name)
    }

    private val startGame: (Event) -> Unit = {_ ->
        presenter.startGame()
    }

    private val checkCountry: (dynamic) -> Unit = { event ->
        val success = presenter.checkCountry(event.target.dataItem.dataContext.name as String)

        js("window.target = event.target")

        if (success) {
            event.target.parent.chart.series._values.forEach { series ->
                when (series.className) {
                    "MapPolygonSeries" -> {
                        series.children._values.forEach { sprite ->
                            if (sprite.className == "MapPolygon" && sprite.dataItem.dataContext.name as String == event.target.dataItem.dataContext.name as String) {
                                sprite.states.removeKey("hover")
                                sprite.setState("success")
                                sprite.events.off("over", overAll)
                                sprite.events.off("out", outAll)
                            }
                        }
                    }
                    "MapImageSeries" -> {
                        series.children._values.forEach { sprite ->
                            if (sprite.className == "MapImage" && sprite.children._values[0].dataItem.dataContext.name as String == event.target.dataItem.dataContext.name as String) {
                                sprite.children._values[0].states.removeKey("hover")
                                sprite.children._values[0].states.setState("success")
                                sprite.events.off("over", overAll)
                                sprite.events.off("out", outAll)
                            }
                        }
                    }
                    else -> console.log("This never happens...")
                }
                Unit
            }
        }
    }

    private val overAll: (dynamic) -> Unit = { event ->
         event.target.parent.chart.series._values.forEach { series ->
             when (series.className) {
                 "MapPolygonSeries" -> {
                     series.children._values.forEach { sprite ->
                         if (sprite.className == "MapPolygon" && sprite.dataItem.dataContext.name as String == event.target.dataItem.dataContext.name as String) {
                             sprite.setState("hover")
                         }
                     }
                 }
                 "MapImageSeries" -> {
                     series.children._values.forEach { sprite ->
                         if (sprite.className == "MapImage" && sprite.children._values[0].dataItem.dataContext.name as String == event.target.dataItem.dataContext.name as String) {
                             sprite.children._values[0].setState("hover")
                         }
                     }
                 }
                 else -> console.log("This never happens...")
             }
             Unit
         }
         Unit
    }

    private val outAll: (dynamic) -> Unit = { event ->
        event.target.parent.chart.series._values.forEach { series ->
            when (series.className) {
                "MapPolygonSeries" -> {
                    series.children._values.forEach { sprite ->
                        if (sprite.className == "MapPolygon" && sprite.dataItem.dataContext.name as String == event.target.dataItem.dataContext.name as String) {
                            sprite.setState("default")
                        }
                    }
                }
                "MapImageSeries" -> {
                    series.children._values.forEach { sprite ->
                        if (sprite.className == "MapImage" && sprite.children._values[0].dataItem.dataContext.name as String == event.target.dataItem.dataContext.name as String) {
                            sprite.children._values[0].setState("default")
                        }
                    }
                }
                else -> console.log("This never happens...")
            }
            Unit
        }
        Unit
    }

    override fun setOptions(options: Set<String>) {
        unregister()

        while (buttonDiv.childElementCount > 0) {
            buttonDiv.removeChild(buttonDiv.childNodes[0]!!)
        }

        for (option in options) {
            val button = document.createElement("button")
            button.setAttribute("name", option)
            button.setAttribute("style", "text-transform: capitalize;")
            button.textContent = normalize(option)
            buttonDiv.append(button)
        }

        register()
    }

    override fun updateTimer(timer: Int) {
        timerElement.textContent = "${if (timer / 60 >= 10) "" else 0}${timer / 60}:${if (timer % 60 >= 10) "" else 0}${timer % 60}"
    }

    override fun displayWin() {
        countryToFindSpan.textContent = "Good Job!"
    }

    override fun displayRegion(regionName: String, geodata: dynamic, include: MutableList<String>?, initialZoom: Double, initialPoint: Coordinate, circles: dynamic) {
        regionElement.textContent = normalize(regionName)
        countryToFindSpan.textContent = ""
        timerElement.textContent = ""

        val map = create(mapDiv.id, MapChart)
        map.geodata = geodata
        map.projection = Miller()
        map.homeZoomLevel = initialZoom
        map.seriesContainer.draggable = false
        map.seriesContainer.resizable = false
        map.seriesContainer.events.disableType("doublehit")
        map.chartContainer.background.events.disableType("doublehit")
        map.maxZoomLevel = initialZoom
        map.minZoomLevel = initialZoom
        map.homeGeoPoint = initialPoint.toObject()

        val mapPolygonSeries = map.series.push(MapPolygonSeries())
        val mapPolygon = mapPolygonSeries.mapPolygons.template
        mapPolygonSeries.useGeodata = true
        if (include != null) {
            mapPolygonSeries.include = nativeArray(include)
        }
        mapPolygon.events.on("hit", checkCountry)
        mapPolygon.events.on("over", overAll)
        mapPolygon.events.on("out", outAll)

        val mapHover = mapPolygon.states.create("hover")
        mapHover.properties.fill = color("#AECAA7")

        val mapSuccess = mapPolygon.states.create("success")
        mapSuccess.properties.fill = color("#228B22")

        val clickableSeries = map.series.push(MapImageSeries())
        val clickable = clickableSeries.mapImages.template
        clickable.nonScaling = true
        clickable.propertyFields.latitude = "latitude"
        clickable.propertyFields.longitude = "longitude"
        clickableSeries.data = circles
        clickable.events.on("hit", checkCountry)
        clickable.events.on("over", overAll)
        clickable.events.on("out", outAll)

        val circle = clickable.createChild(Circle)
        circle.radius = 5
        circle.fill = color("#D9D9D9")
        circle.stroke = color("#000000")
        circle.strokeWidth = 0.25

        val circleHover = circle.states.create("hover")
        circleHover.properties.fill = color("#AECAA7")

        val circleSuccess = circle.states.create("success")
        circleSuccess.properties.fill = color("#228B22")
    }

    override fun displayCountryToFind(country: String) {
        countryToFindSpan.textContent = "Find: $country"
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
