package publications.data.local.exposed

import org.jetbrains.exposed.dao.LongIdTable

object PublicationsTable: LongIdTable("Publications") {
    val lastPublicationTimestamp = long("lastPublicationTimestamp")
    val lastPublicationTimeToLive = integer("lastPublicationTimeToLive")
}