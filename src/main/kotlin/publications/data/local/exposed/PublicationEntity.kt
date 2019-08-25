package publications.data.local.exposed

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class PublicationEntity(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<PublicationEntity>(PublicationsTable)

    var lastPublicationTimestamp by PublicationsTable.lastPublicationTimestamp
    var lastPublicationTimeToLive by PublicationsTable.lastPublicationTimeToLive
}