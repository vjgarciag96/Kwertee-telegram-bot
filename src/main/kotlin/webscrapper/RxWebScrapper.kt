package webscrapper

import com.github.florent37.rxjsoup.RxJsoup
import io.reactivex.Observable
import org.jsoup.Connection

interface RxWebScrapper {
    fun connect(connection: Connection): Observable<Connection.Response>
}

class RxJsoupWebScrapper : RxWebScrapper {
    override fun connect(connection: Connection): Observable<Connection.Response> =
            RxJsoup.connect(connection)
}