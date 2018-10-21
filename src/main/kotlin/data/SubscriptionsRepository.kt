package data

import model.Subscription

class SubscriptionsRepository(
        private val localDataSource: LocalSubscriptionsDataSource
) {

    fun save(subscription: Subscription) {
        localDataSource.save(subscription)
    }
}