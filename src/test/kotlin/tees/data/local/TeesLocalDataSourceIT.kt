package tees.data.local

import core.TimeProvider
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertNotNull
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tees.data.local.exposed.TeeEntity
import tees.data.local.exposed.TeesTable
import tees.domain.TimeToLiveHandler

class TeesLocalDataSourceIT {

    private val database by lazy { Database.connect(url = "jdbc:sqlite:src/test/resources/qwertee.db", driver = "org.sqlite.JDBC") }

    private val timeProviderMock = mockk<TimeProvider>(relaxed = true)
    private val timeToLiveHandlerMock = mockk<TimeToLiveHandler>()

    private val sut = TeesLocalDataSource(
        timeProvider = timeProviderMock,
        timeToLiveHandler = timeToLiveHandlerMock
    )

    @BeforeEach
    fun setUp() {
        transaction(database) {
            SchemaUtils.create(TeesTable)
        }
    }

    @AfterEach
    fun tearDown() {
        transaction(database) {
            SchemaUtils.drop(TeesTable)
        }
    }

    @Test
    fun `fetchGoneForever returns gone forever tees is there are previous valid stored tees`() {
        givenAnyPreviousTeeOnDb(title = "gone forever", teeType = TypeDO.GONE_FOREVER)
        givenAnyPreviousTeeOnDb(title = "last chance", teeType = TypeDO.LAST_CHANCE)
        givenAnyTeeIsValid()

        val fetchGoneForeverResult = sut.fetchGoneForever()

        val expectedResult = listOf(
            tee(
                eurPrice = ANY_PRICE,
                usdPrice = ANY_PRICE,
                gbpPrice = ANY_PRICE,
                title = "gone forever",
                imageUrl = ANY_IMAGE_URL
            )
        )
        assertEquals(expectedResult, fetchGoneForeverResult)
    }

    @Test
    fun `fetchGoneForever returns null if there aren't previous valid stored tees`() {
        givenAnyTeeIsInvalid()

        val fetchGoneForeverResult = sut.fetchGoneForever()

        assertNull(fetchGoneForeverResult)
    }

    @Test
    fun `fetchLastChance returns last chance tees if there are previous valid stored tees`() {
        givenAnyPreviousTeeOnDb(title = "gone forever", teeType = TypeDO.GONE_FOREVER)
        givenAnyPreviousTeeOnDb(title = "last chance", teeType = TypeDO.LAST_CHANCE)
        givenAnyTeeIsValid()

        val fetchLastChanceResult = sut.fetchLastChance()

        val expectedResult = listOf(
            tee(
                eurPrice = ANY_PRICE,
                usdPrice = ANY_PRICE,
                gbpPrice = ANY_PRICE,
                title = "last chance",
                imageUrl = ANY_IMAGE_URL
            )
        )
        assertEquals(expectedResult, fetchLastChanceResult)
    }

    @Test
    fun `fetchLastChance returns null if there aren't previous valid stored tees`() {
        givenAnyTeeIsInvalid()

        val fetchLastChanceResult = sut.fetchLastChance()

        assertNull(fetchLastChanceResult)
    }

    @Test
    fun `fetchPromoted returns null if there are no previous stored tees`() {
        val fetchPromotedResult = sut.fetchPromoted()

        assertNull(fetchPromotedResult)
    }

    @Test
    fun `fetchPromoted returns null if there are previous stored tees but they're invalid`() {
        givenAnyPreviousTeeOnDb()
        givenAnyTeeIsInvalid()

        val fetchPromotedResult = sut.fetchPromoted()

        assertNull(fetchPromotedResult)
    }

    @Test
    fun `fetchPromoted returns tees if there are previous valid stored tees`() {
        givenAnyPreviousTeeOnDb()
        givenAnyTeeIsValid()

        val fetchPromotedResult = sut.fetchPromoted()

        val expectedPromotedTees = PromotedTeesDO(
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
            lastChanceTees = emptyList()
        )
        assertEquals(expectedPromotedTees, fetchPromotedResult)
    }

    @Test
    fun `putPromoted cleans previous tees on db`() {
        givenAnyPreviousTeeOnDb()

        sut.putPromoted(emptyPromotedTees())

        transaction {
            val query = TeesTable.selectAll()
            assertEquals(query.count(), 0)
        }
    }

    @Test
    fun `putPromoted inserts tees on db`() {
        sut.putPromoted(anyPromotedTees())

        transaction {
            val query = TeesTable.selectAll()

            val firstTeeResult = query.firstOrNull()
            assertNotNull(firstTeeResult)
            firstTeeResult!!
            assertEquals(firstTeeResult[TeesTable.id].value, 1)
            assertEquals(firstTeeResult[TeesTable.title], "gone forever tee")

            val secondTeeResult = query.lastOrNull()
            assertNotNull(secondTeeResult)
            secondTeeResult!!
            assertEquals(secondTeeResult[TeesTable.id].value, 2)
            assertEquals(secondTeeResult[TeesTable.title], "last chance tee")
        }
    }

    private fun anyPromotedTees(): PromotedTeesDO = promotedTees(
        goneForeverTees = listOf(tee(title = "gone forever tee")),
        lastChanceTees = listOf(tee(title = "last chance tee"))
    )

    private fun emptyPromotedTees(): PromotedTeesDO = promotedTees()

    private fun givenAnyPreviousTeeOnDb(
        title: String = ANY_TITLE,
        timeToLive: Int = ANY_TIME_TO_LIVE,
        storageTimestamp: Long = ANY_TIMESTAMP_MILLIS,
        teeType: TypeDO = ANY_TEE_TYPE
    ) {
        transaction {
            TeeEntity.new {
                eurPrice = ANY_PRICE
                usdPrice = ANY_PRICE
                gbpPrice = ANY_PRICE
                this.title = title
                imageUrl = ANY_IMAGE_URL
                this.timeToLive = timeToLive
                this.storageTimestamp = storageTimestamp
                this.type = teeType
            }
        }
    }

    private fun givenAnyTeeIsValid() {
        every { timeToLiveHandlerMock.isValid(any(), any()) } returns true
    }

    private fun givenAnyTeeIsInvalid() {
        every { timeToLiveHandlerMock.isValid(any(), any()) } returns false
    }

    private companion object {
        const val ANY_TIME_TO_LIVE = 24234
        const val ANY_TIMESTAMP_MILLIS = 2353553235L
        const val ANY_PRICE = 2
        const val ANY_TITLE = "title"
        const val ANY_IMAGE_URL = "https://www.ruka.com/image.png"
        val ANY_TEE_TYPE = TypeDO.GONE_FOREVER
    }
}