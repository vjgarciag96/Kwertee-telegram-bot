package data.remote

import org.jsoup.Connection

class QwerteeWebScrapper(
    private val webScrapper: WebScrapper,
    private val connection: Connection
) {

    suspend fun getGoneForeverTShirts(): List<TShirtDTO> {
        val response = webScrapper.connect(connection)
        val html = response.parse()
        val tShirtsDivs = html.select("div.index-tee")

        return tShirtsDivs.map {
            TShirtDTO(
                it.attr("data-tee-price-eur"),
                it.attr("data-tee-price-usd"),
                it.attr("data-tee-price-gbp"),
                it.select("div.title div span").text(),
                it.select("div.buy-wrap div.buy div.design-dynamic-image-wrap div.mens-dynamic-image picture img").attr("src")
            )
        }
    }
}