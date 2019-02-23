package countrygame

import amcharts4.*
import org.w3c.dom.*
import org.w3c.dom.events.Event

class WebCountryView(private val buttonDiv: HTMLDivElement, private val mapDiv: HTMLDivElement) : CountryView {
    override lateinit var presenter: CountryPresenter

    private val setCountry: (Event) -> Unit = {event ->
        val button = event.target as HTMLButtonElement
        presenter.setCountry(button.name)
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
            button.addEventListener("click", setCountry)
        }

        val map = create("map", MapChart)
        map.geodata = am4geodata_worldLow
    }

    private fun unregister() {
    }
}
