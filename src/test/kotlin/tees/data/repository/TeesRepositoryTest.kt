package tees.data.repository

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Test
import tees.data.local.TeesLocalDataSource
import tees.data.local.promotedTees
import tees.data.local.tee
import tees.data.remote.PromotedTeesDto
import tees.data.remote.TeesRemoteDataSource
import tees.data.remote.teeDto

class TeesRepositoryTest {

    private val teesRemoteDataSourceMock = mockk<TeesRemoteDataSource>()
    private val teesLocalDataSourceMock = mockk<TeesLocalDataSource>(relaxed = true)

    private val sut = TeesRepository(
        teesRemoteDataSource = teesRemoteDataSourceMock,
        teesLocalDataSource = teesLocalDataSourceMock
    )

    @Test
    fun `fetchPromoted returns local content when there are local tees`() {
        givenThereAreLocalTees()

        val fetchPromotedResult = whenPromotedTeesAreFetched()

        thenLocalContentIsReturned(fetchPromotedResult)
    }

    @Test
    fun `fetchPromoted returns remote content when there are not local tees`() {
        givenThereAreNotLocalTees()
        givenAnyRemoteTees()

        val fetchPromotedResult = whenPromotedTeesAreFetched()

        thenRemoteContentIsReturned(fetchPromotedResult)
    }

    private fun givenThereAreLocalTees() {
        every { teesLocalDataSourceMock.fetchPromoted() } returns promotedTees(
            timeToLive = ANY_TIME_TO_LIVE,
            goneForeverTees = listOf(
                tee(
                    eurPrice = ANY_PRICE,
                    usdPrice = ANY_PRICE,
                    gbpPrice = ANY_PRICE,
                    title = ANY_TITLE,
                    imageUrl = ANY_IMAGE_URL
                )
            ),
            lastChanceTees = listOf(
                tee(
                    eurPrice = ANY_PRICE,
                    usdPrice = ANY_PRICE,
                    gbpPrice = ANY_PRICE,
                    title = ANY_OTHER_TITLE,
                    imageUrl = ANY_IMAGE_URL)
            )
        )
    }

    private fun givenThereAreNotLocalTees() {
        every { teesLocalDataSourceMock.fetchPromoted() } returns null
    }

    private fun givenAnyRemoteTees() {
        every { teesRemoteDataSourceMock.fetchPromoted() } returns PromotedTeesDto(
            timeToLive = ANY_TIME_TO_LIVE,
            goneForeverTees = listOf(
                teeDto(
                    eurPrice = ANY_RAW_PRICE,
                    usdPrice = ANY_RAW_PRICE,
                    gbpPrice = ANY_RAW_PRICE,
                    title = ANY_TITLE,
                    imageUrl = ANY_IMAGE_URL
                )
            ),
            lastChanceTees = listOf(
                teeDto(
                    eurPrice = ANY_RAW_PRICE,
                    usdPrice = ANY_RAW_PRICE,
                    gbpPrice = ANY_RAW_PRICE,
                    title = ANY_OTHER_TITLE,
                    imageUrl = ANY_OTHER_IMAGE_URL
                )
            )
        )
    }

    private fun whenPromotedTeesAreFetched(): PromotedTeesData = sut.fetchPromoted()

    private fun thenLocalContentIsReturned(fetchPromotedResult: PromotedTeesData) {
        val expectedPromotedResult = PromotedTeesData(
            timeToLive = ANY_TIME_TO_LIVE,
            goneForeverTees = listOf(
                TeeData(
                    eurPrice = ANY_PRICE,
                    usdPrice = ANY_PRICE,
                    gbpPrice = ANY_PRICE,
                    title = ANY_TITLE,
                    imageUrl = ANY_IMAGE_URL
                )
            ),
            lastChanceTees = listOf(
                TeeData(
                    eurPrice = ANY_PRICE,
                    usdPrice = ANY_PRICE,
                    gbpPrice = ANY_PRICE,
                    title = ANY_OTHER_TITLE,
                    imageUrl = ANY_IMAGE_URL
                )
            )
        )
        assertEquals(expectedPromotedResult, fetchPromotedResult)
    }

    private fun thenRemoteContentIsReturned(fetchPromotedResult: PromotedTeesData) {
        val expectedPromotedResult = PromotedTeesData(
            timeToLive = ANY_TIME_TO_LIVE,
            goneForeverTees = listOf(
                TeeData(
                    eurPrice = ANY_RAW_PRICE.toInt(),
                    usdPrice = ANY_RAW_PRICE.toInt(),
                    gbpPrice = ANY_RAW_PRICE.toInt(),
                    title = ANY_TITLE,
                    imageUrl = ANY_IMAGE_URL
                )
            ),
            lastChanceTees = listOf(
                TeeData(
                    eurPrice = ANY_RAW_PRICE.toInt(),
                    usdPrice = ANY_RAW_PRICE.toInt(),
                    gbpPrice = ANY_RAW_PRICE.toInt(),
                    title = ANY_OTHER_TITLE,
                    imageUrl = ANY_OTHER_IMAGE_URL
                )
            )
        )
        assertEquals(expectedPromotedResult, fetchPromotedResult)
    }

    private companion object {
        const val ANY_PRICE = 5
        const val ANY_RAW_PRICE = "6"
        const val ANY_TITLE = "ruka title"
        const val ANY_OTHER_TITLE = "other ruka title"
        const val ANY_IMAGE_URL = "https://www.mydrugs.io/rukaina.png"
        const val ANY_OTHER_IMAGE_URL = "https://www.mydrugs.io/rkdma.png"
        const val ANY_TIME_TO_LIVE = 123223
    }
}