package tees.data.local.exposed

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class TeeEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TeeEntity>(TeesTable)

    var eurPrice by TeesTable.eurPrice
    var usdPrice by TeesTable.usdPrice
    var gbpPrice by TeesTable.gbpPrice
    var title by TeesTable.title
    var imageUrl by TeesTable.imageUrl
    var timeToLive by TeesTable.timeToLive
    var storageTimestamp by TeesTable.storageTimestamp
    var type by TeesTable.type
}