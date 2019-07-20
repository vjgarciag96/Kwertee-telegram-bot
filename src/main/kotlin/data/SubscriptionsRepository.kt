package data

import domain.Subscription

class SubscriptionsRepository(
    private val subscriptionsLocalDataSource: SubscriptionsLocalDataSource
) {

    fun fetchAll(): List<Subscription> = subscriptionsLocalDataSource.fetchAll()

    fun put(subscription: Subscription) {
        subscriptionsLocalDataSource.put(subscription)
    }
}