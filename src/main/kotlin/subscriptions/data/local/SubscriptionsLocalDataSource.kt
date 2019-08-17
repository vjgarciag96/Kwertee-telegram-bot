package subscriptions.data.local

import subscriptions.data.local.exposed.SubscriptionTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class SubscriptionsLocalDataSource {

    fun fetchAll(): List<SubscriptionDO> = transaction {
        SubscriptionTable.selectAll().map { it.toSubscriptionDO() }
    }

    fun put(subscription: SubscriptionDO): Boolean =
        transaction {
            val dbSubscription = SubscriptionTable.select {
                SubscriptionTable.userId eq subscription.userId
            }.map { it.toSubscriptionDO() }

            if (dbSubscription.isNotEmpty()) {
                return@transaction false
            }

            SubscriptionTable.insert {
                it[userId] = subscription.userId
                it[username] = subscription.username
            }

            return@transaction true
        }

    fun remove(userId: Long): Boolean =
        transaction {
            val dbSubscription = SubscriptionTable.select {
                SubscriptionTable.userId eq userId
            }.map { it.toSubscriptionDO() }

            if (dbSubscription.isEmpty()) {
                return@transaction false
            }

            SubscriptionTable.deleteWhere {
                SubscriptionTable.userId eq userId
            }

            return@transaction true
        }

    private fun ResultRow.toSubscriptionDO(): SubscriptionDO =
        SubscriptionDO(
            this[SubscriptionTable.userId],
            this[SubscriptionTable.username]
        )
}
