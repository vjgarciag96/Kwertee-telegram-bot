package tees.data.remote

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

    @Test
    fun `Gets gone forever`() {
        givenAnyMockedQwerteeHtml()

        val goneForeverTeesResult = sut.getGoneForeverTShirts()

        assertEquals(expectedGoneForeverTees(), goneForeverTeesResult)
    }

    @Test
    fun `Gets last chance`() {
        givenAnyMockedQwerteeHtml()

        val lastChanceTeesResult = sut.getLastChanceTees()

        assertEquals(expectedLastChanceTees(), lastChanceTeesResult)
    }

    @Test
    fun `Fetches promoted tees`() {
        givenAnyMockedQwerteeHtml()

        val promotedTeesResult = sut.fetchPromoted()

        val expectedPromotedTees = PromotedTeesDto(
            timeToLive = 95820,
            goneForeverTees = expectedGoneForeverTees(),
            lastChanceTees = expectedLastChanceTees()
        )
        assertEquals(expectedPromotedTees, promotedTeesResult)
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

    private fun expectedLastChanceTees(): List<TeeDto> =
        listOf(
            TeeDto(
                eurPrice = "13",
                usdPrice = "14",
                gbpPrice = "11",
                title = "Le Petit Magicien Noir",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565262638-151127-mens-450x540.jpg"
            ),
            TeeDto(
                eurPrice = "13",
                usdPrice = "14",
                gbpPrice = "11",
                title = "Nier:2B",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565179403-150984-mens-450x540.jpg"
            ),
            TeeDto(
                eurPrice = "13",
                usdPrice = "14",
                gbpPrice = "11",
                title = "White Wolf God",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565262714-151576-mens-450x540.jpg"
            )
        )

    private fun expectedGoneForeverTees(): List<TeeDto> =
        listOf(
            TeeDto(
                eurPrice = "11",
                usdPrice = "12",
                gbpPrice = "9",
                title = "How to Deal With My Feelings",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565778110-151549-mens-450x540.jpg"
            ),
            TeeDto(
                eurPrice = "11",
                usdPrice = "12",
                gbpPrice = "9",
                title = "Calavera Witched Cat",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565705530-151471-mens-450x540.jpg"
            ),
            TeeDto(
                eurPrice = "11",
                usdPrice = "12",
                gbpPrice = "9",
                title = "it's all fine",
                imageUrl = "https://cdn.qwertee.com/images/designs/product-thumbs/1565705462-151292-mens-450x540.jpg"
            )
        )

    private companion object {
        const val EMPTY_PATH = ""
    }
}