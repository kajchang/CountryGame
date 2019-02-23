package countrygame

interface CountryView {
    var presenter: CountryPresenter
    fun dispose()
}
