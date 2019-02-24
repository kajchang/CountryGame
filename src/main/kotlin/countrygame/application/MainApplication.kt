package countrygame.application

import org.w3c.dom.*
import kotlin.browser.*

class MainApplication : ApplicationBase() {
    private lateinit var view: WebCountryView
    private lateinit var presenter: CountryPresenter

    override val stateKeys = listOf("country")

    override fun start(state: Map<String, Any>) {
        view = WebCountryView(
                document.getElementById("country-buttons")!! as HTMLDivElement,
                document.getElementById("map")!! as HTMLDivElement,
                document.getElementById("region")!!)
        presenter = CountryPresenter(view)

        state["country"]?.let { country ->
            presenter.restore(country as String)
        }
    }

    override fun dispose() = mapOf(
            "country" to presenter.dispose()
    )
}
