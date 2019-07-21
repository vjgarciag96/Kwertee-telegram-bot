package data.local

import domain.Subscription

class SubscriptionsLocalDataSource {

    private val inMemoryStorage = mutableListOf<SubscriptionDO>()

    fun fetchAll(): List<SubscriptionDO> = inMemoryStorage

    fun put(subscription: SubscriptionDO) {
        inMemoryStorage.add(subscription)
    }
}
