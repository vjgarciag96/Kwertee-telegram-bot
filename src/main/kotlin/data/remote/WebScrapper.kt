package data.remote

import org.jsoup.Connection

class WebScrapper {
    suspend fun connect(connection: Connection): Connection.Response = connection.execute()
}