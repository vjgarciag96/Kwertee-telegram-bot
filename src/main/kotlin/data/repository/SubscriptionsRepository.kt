package data.repository

import data.local.SubscriptionDO
import data.local.SubscriptionsLocalDataSource
import data.local.toDomain
import domain.Subscription

class SubscriptionsRepository(
    private val subscriptionsLocalDataSource: SubscriptionsLocalDataSource
) {

    fun fetchAll(): List<Subscription> =
        subscriptionsLocalDataSource.fetchAll().map(SubscriptionDO::toDomain)

    fun put(subscription: Subscription) {
        subscriptionsLocalDataSource.put(subscription.toDiskObject())
    }

    fun remove(userId: Long) {
        subscriptionsLocalDataSource.remove(userId)
    }
}