package data

import model.Subscription

class SubscriptionsRepository(
    private val subscriptionsLocalDataSource: SubscriptionsLocalDataSource
) {

    fun put(subscription: Subscription) {
        subscriptionsLocalDataSource.put(subscription)
    }
}