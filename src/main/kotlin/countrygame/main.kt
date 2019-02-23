package countrygame

import org.w3c.dom.HTMLDivElement
import kotlin.browser.document
import kotlin.dom.appendText

fun main(args: Array<String>) {
    val div = document.getElementById("app") as HTMLDivElement
    div.appendText("Hello World")
}
