package publications.data.local.exposed

import org.jetbrains.exposed.sql.ResultRow
import publications.data.local.PublicationDO

fun ResultRow.toDiskObject(): PublicationDO = PublicationDO(
    timestamp = this[PublicationsTable.lastPublicationTimestamp],
    timeToLive = this[PublicationsTable.lastPublicationTimeToLive]
)