package subscriptions.data.repository

import subscriptions.data.local.SubscriptionDO
import subscriptions.data.local.SubscriptionsLocalDataSource
import subscriptions.data.local.toDomain
import subscriptions.domain.Subscription

class SubscriptionsRepository(
    private val subscriptionsLocalDataSource: SubscriptionsLocalDataSource
) {

    fun fetchAll(): List<Subscription> =
        subscriptionsLocalDataSource.fetchAll().map(SubscriptionDO::toDomain)

    fun put(subscription: Subscription): Boolean =
        subscriptionsLocalDataSource.put(subscription.toDiskObject())

    fun remove(userId: Long): Boolean = subscriptionsLocalDataSource.remove(userId)
}