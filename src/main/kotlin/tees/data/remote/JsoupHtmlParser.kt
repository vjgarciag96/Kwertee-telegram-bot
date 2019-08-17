package tees.data.remote

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class JsoupHtmlParser {
    fun parse(html: String): Document = Jsoup.parse(html)
}