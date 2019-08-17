package data.remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class QwerteeWebScrapperIT {

    private val mockWebServer = MockWebServer()

    private val okHttpClient = OkHttpClient.Builder().build()
    private val jsoupHtmlParser = JsoupHtmlParser()
    private lateinit var qwerteeConfig: QwerteeConfig

    private lateinit var sut: QwerteeWebScrapper

    @BeforeEach
    fun setUp() {
        mockWebServer.start()
        val webServerUrl = mockWebServer.url(EMPTY_PATH)
        qwerteeConfig = QwerteeConfig(baseUrl = webServerUrl.toString())
        sut = QwerteeWebScrapper(qwerteeConfig, okHttpClient, jsoupHtmlParser)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Gets gone forever t-shirts`() = runBlockingTest {
        givenAnyMockedQwerteeHtml()

        val tShirtsResult = sut.getGoneForeverTShirts()

        val expectedTShirts = listOf(
            TShirtDTO(
                eurPrice = "11",
                usdPrice = "12",
                gbpPrice = "9",
                title = "How to Deal With My Feelings",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565778110-151549-mens-450x540.jpg"
            ),
            TShirtDTO(
                eurPrice = "11",
                usdPrice = "12",
                gbpPrice = "9",
                title = "Calavera Witched Cat",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565705530-151471-mens-450x540.jpg"
            ),
            TShirtDTO(
                eurPrice = "11",
                usdPrice = "12",
                gbpPrice = "9",
                title = "it's all fine",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565705462-151292-mens-450x540.jpg"
            )
        )
        assertEquals(expectedTShirts, tShirtsResult)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Gets last chance`() = runBlockingTest {
        givenAnyMockedQwerteeHtml()

        val tShirtsResult = sut.getLastChanceTees()

        val expectedTShirts = listOf(
            TShirtDTO(
                eurPrice = "13",
                usdPrice = "14",
                gbpPrice = "11",
                title = "Le Petit Magicien Noir",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565262638-151127-mens-450x540.jpg"
            ),
            TShirtDTO(
                eurPrice = "13",
                usdPrice = "14",
                gbpPrice = "11",
                title = "Nier:2B",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565179403-150984-mens-450x540.jpg"
            ),
            TShirtDTO(
                eurPrice = "13",
                usdPrice = "14",
                gbpPrice = "11",
                title = "White Wolf God",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565262714-151576-mens-450x540.jpg"
            )
        )
        assertEquals(expectedTShirts, tShirtsResult)
    }

    private fun givenAnyMockedQwerteeHtml() {
        val qwerteeHtml = QwerteeWebScrapperIT::class.java
            .getResource("/qwertee.html")
            .readText()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(qwerteeHtml)
        )
    }

    private companion object {
        const val EMPTY_PATH = ""
    }
}