package webscrapper

import io.reactivex.Observable
import model.TShirtDTO
import org.jsoup.Connection

class QwerteeWebScrapper(
    private val rxWebScrapper: RxWebScrapper,
    private val connection: Connection
) {

    fun getGoneForeverTShirts(): Observable<List<TShirtDTO>> {
        return rxWebScrapper.connect(connection).map {
            val html = it.parse()
            val tShirtsDivs = html.select("div.index-tee")

            val tShirts = arrayListOf<TShirtDTO>()
            tShirtsDivs.forEach {
                tShirts.add(TShirtDTO(
                        it.attr("data-tee-price-eur"),
                        it.attr("data-tee-price-usd"),
                        it.attr("data-tee-price-gbp"),
                        it.select("div.title div span").text(),
                        it.select("div.buy-wrap div.buy div.design-dynamic-image-wrap div.mens-dynamic-image picture img").attr("src")
                ))
            }
            return@map tShirts
        }
    }
}