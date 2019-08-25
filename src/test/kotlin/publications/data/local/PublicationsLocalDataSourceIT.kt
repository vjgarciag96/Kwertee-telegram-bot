package publications.data.local

import core.TimeProvider
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import publications.data.local.exposed.PublicationEntity
import publications.data.local.exposed.PublicationsTable

class PublicationsLocalDataSourceIT {

    private val database by lazy { Database.connect(url = "jdbc:sqlite:src/test/resources/qwertee.db", driver = "org.sqlite.JDBC") }

    private val timeProviderMock = mockk<TimeProvider>(relaxed = true)

    private val sut = PublicationsLocalDataSource(
        timeProvider = timeProviderMock
    )

    @BeforeEach
    fun setUp() {
        transaction(database) {
            SchemaUtils.create(PublicationsTable)
        }
    }

    @AfterEach
    fun tearDown() {
        transaction(database) {
            SchemaUtils.drop(PublicationsTable)
        }
    }

    @Test
    fun `getLastPublication returns last publication if there are previous publications`() {
        givenThereArePreviousPublications()

        val publicationResult = sut.getLastPublicationInfo()

        val expectedPublication = PublicationDO(ANY_TIMESTAMP, ANY_TIME_TO_LIVE)
        assertEquals(expectedPublication, publicationResult)
    }

    @Test
    fun `getLastPublication returns null if there aren't previous publications`() {
        val publicationResult = sut.getLastPublicationInfo()

        assertNull(publicationResult)
    }

    @Test
    fun `setLastPublicationTimeToLive cleans previous publications`() {
        givenThereArePreviousPublications()

        sut.setLastPublicationTimeToLive(ANY_OTHER_TIME_TO_LIVE)

        thenPreviousPublicationsAreCleaned()
    }

    @Test
    fun `setLastPublicationTimeToLive stores time to live with current time millis`() {
        givenAnyCurrentTimeMillis(ANY_TIMESTAMP)

        sut.setLastPublicationTimeToLive(ANY_TIME_TO_LIVE)

        thenTimeToLiveIsStoredWithCurrentTimeMillis(ANY_TIME_TO_LIVE, ANY_TIMESTAMP)
    }

    private fun givenThereArePreviousPublications() {
        transaction {
            PublicationEntity.new {
                lastPublicationTimeToLive = ANY_TIME_TO_LIVE
                lastPublicationTimestamp = ANY_TIMESTAMP
            }
        }
    }

    private fun givenAnyCurrentTimeMillis(timestamp: Long) {
        every { timeProviderMock.currentTimeMillis() } returns timestamp
    }

    private fun thenPreviousPublicationsAreCleaned() {
        transaction {
            val publicationQueryResult = PublicationEntity.find {
                val timeToLiveExpression = PublicationsTable.lastPublicationTimeToLive eq ANY_TIME_TO_LIVE
                val timestampExpression = PublicationsTable.lastPublicationTimestamp eq ANY_TIMESTAMP
                return@find timeToLiveExpression and timestampExpression
            }

            assertTrue(publicationQueryResult.empty())
        }
    }

    private fun thenTimeToLiveIsStoredWithCurrentTimeMillis(timeToLive: Int, timestamp: Long) {
        transaction {
            val publicationQueryResult = PublicationEntity.find {
                val timeToLiveExpression = PublicationsTable.lastPublicationTimeToLive eq timeToLive
                val timestampExpression = PublicationsTable.lastPublicationTimestamp eq timestamp
                return@find timeToLiveExpression and timestampExpression
            }

            val firstResult = publicationQueryResult.firstOrNull()
            assertNotNull(firstResult)
            firstResult!!
            assertEquals(firstResult.lastPublicationTimeToLive, timeToLive)
            assertEquals(firstResult.lastPublicationTimestamp, timestamp)
        }
    }

    private companion object {
        const val ANY_TIMESTAMP = 132412423L
        const val ANY_TIME_TO_LIVE = 21323
        const val ANY_OTHER_TIME_TO_LIVE = 23532
    }
}