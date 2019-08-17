package subscriptions.domain

import subscriptions.data.repository.SubscriptionsRepository
import subscriptions.domain.Subscription

class GetSubscriptions(private val subscriptionsRepository: SubscriptionsRepository) {

    operator fun invoke(): List<Subscription> = subscriptionsRepository.fetchAll()
}