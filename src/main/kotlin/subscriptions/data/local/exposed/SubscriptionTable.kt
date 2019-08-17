package subscriptions.data.local.exposed

import org.jetbrains.exposed.sql.Table

object SubscriptionTable : Table("Subscription") {
    val userId = long("userId").primaryKey()
    val username = varchar("username", 200)
}