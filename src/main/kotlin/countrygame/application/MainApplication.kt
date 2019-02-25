package countrygame.application

import org.w3c.dom.*
import kotlin.browser.*

class MainApplication : ApplicationBase {
    private lateinit var view: WebCountryView
    private lateinit var presenter: CountryPresenter

    override val stateKeys = listOf("country")

    override fun start(state: Map<String, Any>) {
        view = WebCountryView(
                document.getElementById("country-buttons")!! as HTMLDivElement,
                document.getElementById("start")!! as HTMLButtonElement,
                document.getElementById("country-to-find")!!,
                document.getElementById("map")!! as HTMLDivElement,
                document.getElementById("region")!!)
        presenter = CountryPresenter(view)

        state["gameState"]?.let {gameState ->
            @Suppress("UNCHECKED_CAST")
            presenter.restore(gameState as Map<String, Any?>)
        }
    }

    override fun dispose() = mapOf(
            "gameState" to presenter.dispose()
    )
}
