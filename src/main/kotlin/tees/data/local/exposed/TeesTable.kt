package tees.data.local.exposed

import org.jetbrains.exposed.dao.LongIdTable
import tees.data.local.TypeDO

object TeesTable : LongIdTable("Tees") {
    val eurPrice = integer("eurPrice")
    val usdPrice = integer("usdPrice")
    val gbpPrice = integer("gbpPrice")
    val title = text("title")
    val imageUrl = text("imageUrl")
    val timeToLive = integer("timeToLive")
    val storageTimestamp = long("storageTimestamp")
    val type = enumeration("type", TypeDO::class)
}