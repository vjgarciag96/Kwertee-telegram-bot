package subscriptions.data.repository

import subscriptions.data.local.SubscriptionDO
import subscriptions.domain.Subscription

fun Subscription.toDiskObject(): SubscriptionDO = SubscriptionDO(userId, username)