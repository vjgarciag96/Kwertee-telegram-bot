package data.local

import data.local.exposed.SubscriptionTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SubscriptionsLocalDataSource {

    fun fetchAll(): List<SubscriptionDO> = transaction {
        SubscriptionTable.selectAll().map {
            SubscriptionDO(
                it[SubscriptionTable.userId],
                it[SubscriptionTable.username]
            )
        }
    }

    fun put(subscription: SubscriptionDO) {
        transaction {
            SubscriptionTable.insert {
                it[userId] = subscription.userId
                it[username] = subscription.username
            }
        }
    }

    fun remove(userId: Long) {
        transaction {
            SubscriptionTable.deleteWhere {
                SubscriptionTable.userId eq userId
            }
        }
    }
}
