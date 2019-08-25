package publications.data.local

import core.TimeProvider
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import publications.data.local.exposed.PublicationEntity
import publications.data.local.exposed.PublicationsTable
import publications.data.local.exposed.toDiskObject

class PublicationsLocalDataSource(private val timeProvider: TimeProvider) {

    fun getLastPublicationInfo(): PublicationDO? = transaction {
        PublicationsTable.selectAll().limit(1).firstOrNull()?.toDiskObject()
    }

    fun setLastPublicationTimeToLive(timeToLive: Int) {
        transaction {
            cleanPublications()
            storeNewPublication(timeToLive)
        }
    }

    private fun cleanPublications() {
        PublicationsTable.deleteAll()
    }

    private fun storeNewPublication(timeToLive: Int) {
        val publicationTimestamp = timeProvider.currentTimeMillis()
        PublicationEntity.new {
            lastPublicationTimeToLive = timeToLive
            lastPublicationTimestamp = publicationTimestamp
        }
    }
}