package data.local

import domain.Subscription

class SubscriptionsLocalDataSource {

    private val inMemoryStorage = mutableListOf<Subscription>()

    fun fetchAll(): List<Subscription> = inMemoryStorage

    fun put(subscription: Subscription) {
        inMemoryStorage.add(subscription)
    }
}
