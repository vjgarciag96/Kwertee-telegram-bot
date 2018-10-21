package domain

import data.SubscriptionsRepository
import model.Subscription

class Subscribe(private val repository: SubscriptionsRepository) {
    operator fun invoke(userId: Long, username: String) {
        repository.save(Subscription(userId, username))
    }
}