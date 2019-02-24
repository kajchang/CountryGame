package countrygame

import countrygame.application.ApplicationBase
import countrygame.application.MainApplication
import kotlin.browser.*

fun main() {
    var application: ApplicationBase? = null

    val state: dynamic = module.hot?.let { hot ->
        hot.accept()

        hot.dispose { data ->
            data.appState = application?.dispose()
            application = null
        }

        hot.data
    }

    if (document.body != null) {
        application = start(state)
    } else {
        application = null
        document.addEventListener("DOMContentLoaded", { application = start(state) })
    }
}

fun start(state: dynamic): ApplicationBase {
    val application = MainApplication()
    application.start(state?.appState as? Map<String, Any> ?: emptyMap())
    return application
}
