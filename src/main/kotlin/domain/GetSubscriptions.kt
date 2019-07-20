package domain

import data.SubscriptionsRepository
import model.Subscription

class GetSubscriptions(
    private val subscriptionsRepository: SubscriptionsRepository
) {

    operator fun invoke(): List<Subscription> = subscriptionsRepository.fetchAll()
}