package webscrapper

import io.reactivex.Observable
import org.jsoup.Connection
import model.GoneForeverTShirtDTO

class QwerteeWebScrapper(private val rxWebScrapper: RxWebScrapper,
                         private val connection: Connection) {

    fun getGoneForeverTShirts(): Observable<List<GoneForeverTShirtDTO>> {
        return rxWebScrapper.connect(connection).map {
            val html = it.parse()
            val tShirtsDivs = html.select("div.index-tee")

            val tShirts = arrayListOf<GoneForeverTShirtDTO>()
            tShirtsDivs.forEach {
                tShirts.add(GoneForeverTShirtDTO(
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