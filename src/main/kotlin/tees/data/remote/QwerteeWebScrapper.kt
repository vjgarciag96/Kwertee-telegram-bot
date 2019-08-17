package tees.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class QwerteeConfig(
    val baseUrl: String
)

class QwerteeWebScrapper(
    private val qwerteeConfig: QwerteeConfig,
    private val okHttpClient: OkHttpClient,
    private val jsoupHtmlParser: JsoupHtmlParser
) {

    // TODO: Replace List<TeeDto> by Either<L, R> to propagate internal errors
    // TODO: handle execute() IOException
    suspend fun getGoneForeverTShirts(): List<TeeDto> {
        val rawQwerteeHtml = getQwerteeHtml() ?: return emptyList()

        val qwerteeHtml = jsoupHtmlParser.parse(rawQwerteeHtml)
        val goneForeverTShirtsContainer = qwerteeHtml.select(".big-slides").first()
        val goneForeverTShirtsDivs = goneForeverTShirtsContainer.select(".index-tee")
        return goneForeverTShirtsDivs.toTShirtDtos()
    }

    suspend fun getLastChanceTees(): List<TeeDto> {
        val rawQwerteeHtml = getQwerteeHtml() ?: return emptyList()

        val qwerteeHtml = jsoupHtmlParser.parse(rawQwerteeHtml)
        val lastChanceTeesContainer = qwerteeHtml.select(".big-slides").last()
        val lastChanceTeesDivs = lastChanceTeesContainer.select(".index-tee")
        return lastChanceTeesDivs.toTShirtDtos()
    }

    private fun getQwerteeHtml(): String? {
        val request = Request.Builder().url(qwerteeConfig.baseUrl).build()
        val response = okHttpClient.newCall(request).execute()

        return response.body?.string()
    }

    private fun Elements.toTShirtDtos(): List<TeeDto> = map { element -> element.toTShirtDto() }

    private fun Element.toTShirtDto(): TeeDto =
        TeeDto(
            eurPrice = attr("data-tee-price-eur"),
            usdPrice = attr("data-tee-price-usd"),
            gbpPrice = attr("data-tee-price-gbp"),
            title = select("div.title div span").text(),
            imageUrl = select("div.buy-wrap div.buy div.design-dynamic-image-wrap div.mens-dynamic-image picture img").attr("src")
        )
}