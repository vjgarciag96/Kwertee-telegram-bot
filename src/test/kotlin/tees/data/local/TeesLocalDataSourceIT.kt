package tees.data.local

import core.TimeProvider
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.*
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
    fun `fetchPromoted returns null if there are no previous stored tees`() {
        val fetchPromotedResult = sut.fetchPromoted()

        assertNull(fetchPromotedResult)
    }

    @Test
    fun `fetchPromoted returns null if there are previous stored tees but they're invalid`() {
        givenThereArePreviousTeesOnDb(
            timeToLive = 0,
            storageTimestamp = ANY_TIMESTAMP_MILLIS
        )
        givenAnyCurrentTimeMillis(ANY_TIMESTAMP_MILLIS + 1)

        val fetchPromotedResult = sut.fetchPromoted()

        assertNull(fetchPromotedResult)
    }

    @Test
    fun `fetchPromoted returns tees if there are previous valid stored tees`() {
        givenThereArePreviousTeesOnDb()
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
        givenThereArePreviousTeesOnDb()

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

    private fun givenThereArePreviousTeesOnDb(
        timeToLive: Int = ANY_TIME_TO_LIVE,
        storageTimestamp: Long = ANY_TIMESTAMP_MILLIS
    ) {
        transaction {
            TeeEntity.new {
                eurPrice = ANY_PRICE
                usdPrice = ANY_PRICE
                gbpPrice = ANY_PRICE
                title = ANY_TITLE
                imageUrl = ANY_IMAGE_URL
                this.timeToLive = timeToLive
                this.storageTimestamp = storageTimestamp
                this.type = ANY_TEE_TYPE
            }
        }
    }

    private fun givenAnyCurrentTimeMillis(anyTimestamp: Long = ANY_TIMESTAMP_MILLIS) {
        every { timeProviderMock.currentTimeMillis() } returns anyTimestamp
    }

    private fun givenAnyTeeIsValid() {
        every { timeToLiveHandlerMock.isValid(any(), any()) } returns true
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